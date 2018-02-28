package org.provotum.backend.security;

import org.provotum.backend.config.SecurityConfiguration;
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
import java.util.logging.Logger;

@Component
public class EncryptionManager {

    private static final Logger logger = Logger.getLogger(EncryptionManager.class.getName());

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private KeyPair rsaKeyPair;
    private List<ModInteger> voteDomain;

    public EncryptionManager(SecurityConfiguration securityConfiguration) {
        this.publicKey = securityConfiguration.getPublicKey();
        this.privateKey = securityConfiguration.getPrivateKey();
        this.rsaKeyPair = securityConfiguration.getRsaKeyPair();

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
        CipherText cipherText = encryption.encrypt(this.publicKey, votingMessage);
        logger.info("Vote encrypted.");

        // We also need to add the random value used to the public parameters.
        // Since this value must be private under any circumstances, we need to encrypt that...
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // encrypt the plaintext using the public key
        cipher.init(Cipher.ENCRYPT_MODE, this.rsaKeyPair.getPublic());
        byte[] randomValueCipherText = cipher.doFinal(cipherText.getR().finalized().toByteArray());

        logger.info("Generating proof.");
        MembershipProof proof = MembershipProof.commit(this.publicKey, votingMessage, cipherText, this.voteDomain);
        logger.info("Proof generated.");

        // serialize both messages
        String serializedCiphertext = CipherTextSerializer.serialize(cipherText);
        String serializedProof = MembershipProofSerializer.serialize(proof);

        return new CipherTextWrapper(
            serializedCiphertext,
            serializedProof,
            randomValueCipherText
        );
    }

    public CipherText deserializeCiphertext(String ciphertext, byte[] encryptedRandom) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.rsaKeyPair.getPrivate());
        BigInteger random = new BigInteger(cipher.doFinal(encryptedRandom));

        return CipherTextSerializer.fromString(ciphertext, new ModInteger(random.toString(), this.publicKey.getQ()));
    }


    public MembershipProof deserializeMembershipProof(String proof) {
        return MembershipProofSerializer.fromString(proof);
    }

    public ModInteger decrypt(CipherText cipherText) {
        IHomomorphicEncryption<CipherText> encryption = new Encryption();

        return encryption.decrypt(this.privateKey, cipherText);
    }

    public boolean verifyProof(CipherText cipherText, IMembershipProof<CipherText> proof) {
        return proof.verify(this.publicKey, cipherText, this.voteDomain);
    }
}
