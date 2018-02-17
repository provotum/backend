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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class EncryptionManager {

    private static final Logger logger = Logger.getLogger(EncryptionManager.class.getName());

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private List<ModInteger> voteDomain;

    public EncryptionManager(SecurityConfiguration securityConfiguration) {
        this.publicKey = securityConfiguration.getPublicKey();
        this.privateKey = securityConfiguration.getPrivateKey();

        // add the values which we currently accept as a valid vote
        this.voteDomain = new ArrayList<>();
        this.voteDomain.add(ModInteger.ZERO);
        this.voteDomain.add(ModInteger.ONE);
    }

    public CipherText generateZeroVote() {
        ModInteger votingMessage = new ModInteger(0, this.publicKey.getP());
        IHomomorphicEncryption<CipherText> encryption = new Encryption();

        return encryption.encrypt(this.publicKey, votingMessage);
    }

    public CipherTextWrapper encryptVoteAndGenerateProof(int vote) {
        logger.info("Starting to encrypt vote and generate corresponding proof.");

        // set the vote modulus the prime number
        ModInteger votingMessage = new ModInteger(vote, this.publicKey.getP());

        // encrypt the message
        logger.info("Starting to encrypt vote.");
        IHomomorphicEncryption<CipherText> encryption = new Encryption();
        CipherText cipherText = encryption.encrypt(this.publicKey, votingMessage);
        logger.info("Vote encrypted.");

        logger.info("Generating proof.");
        MembershipProof proof = MembershipProof.commit(this.publicKey, votingMessage, cipherText, this.voteDomain);
        logger.info("Proof generated.");

        // serialize both messages
        String serializedCiphertext = CipherTextSerializer.serialize(cipherText);
        String serializedProof = MembershipProofSerializer.serialize(proof);

        return new CipherTextWrapper(
            serializedCiphertext,
            serializedProof
        );
    }

    public ModInteger decrypt(CipherText cipherText) {
        IHomomorphicEncryption<CipherText> encryption = new Encryption();

        return encryption.decrypt(this.privateKey, cipherText);
    }

    public boolean verifyProof(CipherText cipherText, IMembershipProof<CipherText> proof) {
        return proof.verify(this.publicKey, cipherText, this.voteDomain);
    }
}
