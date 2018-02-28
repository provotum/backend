package org.provotum.backend.security;

import org.provotum.backend.config.SecurityConfiguration;
import org.provotum.backend.timer.EvaluationTimer;
import org.provotum.security.api.IHomomorphicEncryption;
import org.provotum.security.api.IMembershipProof;
import org.provotum.security.arithmetic.ModInteger;
import org.provotum.security.elgamal.PrivateKey;
import org.provotum.security.elgamal.PublicKey;
import org.provotum.security.elgamal.additive.CipherText;
import org.provotum.security.elgamal.additive.Encryption;
import org.provotum.security.elgamal.proof.noninteractive.MembershipProof;
import org.provotum.security.serializer.CipherTextSerializer;
import org.provotum.security.serializer.MembershipProofSerializer;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class EncryptionManager {

    private static final Logger logger = Logger.getLogger(EncryptionManager.class.getName());

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private KeyPair rsaKeyPair;
    private List<ModInteger> voteDomain;
    private EvaluationTimer timer;

    public EncryptionManager(SecurityConfiguration securityConfiguration, EvaluationTimer timer) {
        this.publicKey = securityConfiguration.getPublicKey();
        this.privateKey = securityConfiguration.getPrivateKey();
        this.rsaKeyPair = securityConfiguration.getRsaKeyPair();
        this.timer = timer;

        // add the values which we currently accept as a valid vote
        this.voteDomain = new ArrayList<>();
        this.voteDomain.add(ModInteger.ZERO);
        this.voteDomain.add(ModInteger.ONE);
    }

    public CipherText generateZeroVote() {
        ModInteger votingMessage = new ModInteger("0", this.publicKey.getP());
        IHomomorphicEncryption<CipherText> encryption = new Encryption();

        return encryption.encrypt(this.publicKey, votingMessage);
    }

    public CipherTextWrapper encryptVoteAndGenerateProof(int vote) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        logger.info("Starting to encrypt vote and generate corresponding proof.");

        // set the vote modulus the prime number
        ModInteger votingMessage = new ModInteger(Integer.toString(vote), this.publicKey.getP());

        // encrypt the message
        logger.info("Starting to encrypt vote.");
        IHomomorphicEncryption<CipherText> encryption = new Encryption();
        UUID uuid = this.timer.start();
        CipherText cipherText = encryption.encrypt(this.publicKey, votingMessage);
        EvaluationTimer.Duration duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.ENCRYPTION_CIPHERTEXT, duration);
        logger.info("Vote encrypted.");

        // We also need to add the random value used to the public parameters.
        // Since this value must be private under any circumstances, we need to encrypt that...
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // encrypt the plaintext using the public key
        cipher.init(Cipher.ENCRYPT_MODE, this.rsaKeyPair.getPublic());
        uuid = this.timer.start();
        byte[] randomValueCipherText = cipher.doFinal(cipherText.getR().finalized().toByteArray());
        duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.ENCRYPTION_RANDOM, duration);

        logger.info("Generating proof.");
        uuid = this.timer.start();
        MembershipProof proof = MembershipProof.commit(this.publicKey, votingMessage, cipherText, this.voteDomain);
        duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.GENERATING_PROOF, duration);
        logger.info("Proof generated.");

        // serialize both messages
        uuid = this.timer.start();
        String serializedCiphertext = CipherTextSerializer.serialize(cipherText);
        duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.SERIALIZATION_CIPHERTEXT, duration);
        uuid = this.timer.start();
        String serializedProof = MembershipProofSerializer.serialize(proof);
        duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.SERIALIZATION_PROOF, duration);

        return new CipherTextWrapper(
            serializedCiphertext,
            serializedProof,
            randomValueCipherText
        );
    }

    public CipherText deserializeCiphertext(String ciphertext, byte[] encryptedRandom) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.rsaKeyPair.getPrivate());
        UUID uuid = this.timer.start();
        BigInteger random = new BigInteger(cipher.doFinal(encryptedRandom));
        EvaluationTimer.Duration duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.DECRYPTION_RANDOM, duration);

        uuid = this.timer.start();
        CipherText cipherText = CipherTextSerializer.fromString(ciphertext, new ModInteger(random.toString(), this.publicKey.getQ()));
        duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.DESERIALIZATION_CIPHERTEXT, duration);

        return cipherText;
    }


    public MembershipProof deserializeMembershipProof(String proof) {
        UUID uuid = this.timer.start();
        MembershipProof membershipProof = MembershipProofSerializer.fromString(proof);
        EvaluationTimer.Duration duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.DESERIALIZATION_PROOF, duration);

        return membershipProof;
    }

    public ModInteger decryptSum(CipherText cipherText) {
        IHomomorphicEncryption<CipherText> encryption = new Encryption();
        UUID uuid = this.timer.start();
        ModInteger result = encryption.decrypt(this.privateKey, cipherText);
        EvaluationTimer.Duration duration = this.timer.end(uuid);
        this.timer.logDuration(EvaluationTimer.LogCategory.DECRYPTION_CIPHERTEXT, duration);

        return result;
    }

    public boolean verifyProof(CipherText cipherText, IMembershipProof<CipherText> proof) {
        UUID uuid = this.timer.start();
        boolean isSuccess = proof.verify(this.publicKey, cipherText, this.voteDomain);
        EvaluationTimer.Duration duration = this.timer.end(uuid);

        if (isSuccess) {
            this.timer.logDuration(EvaluationTimer.LogCategory.VERIFICATION_PROOF_SUCCESSFUL, duration);
        } else {
            this.timer.logDuration(EvaluationTimer.LogCategory.VERIFICATION_PROOF_UNSUCCESSFUL, duration);
        }

        return isSuccess;
    }
}
