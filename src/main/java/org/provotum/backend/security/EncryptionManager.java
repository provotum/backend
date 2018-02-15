package org.provotum.backend.security;

import org.provotum.backend.config.SecurityConfiguration;
import org.provotum.security.api.IHomomorphicEncryption;
import org.provotum.security.api.IMembershipProofFactory;
import org.provotum.security.arithmetic.ModInteger;
import org.provotum.security.elgamal.PublicKey;
import org.provotum.security.elgamal.additive.CipherText;
import org.provotum.security.elgamal.additive.Encryption;
import org.provotum.security.elgamal.proof.AdditiveElGamalMembershipProofFactory;
import org.provotum.security.elgamal.proof.noninteractive.MembershipProof;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EncryptionManager {

    private PublicKey publicKey;
    private List<ModInteger> voteDomain;

    public EncryptionManager(SecurityConfiguration securityConfiguration) {
        this.publicKey = securityConfiguration.getPublicKey();

        // add the values which we currently accept as a valid vote
        this.voteDomain = new ArrayList<>();
        this.voteDomain.add(ModInteger.ZERO);
        this.voteDomain.add(ModInteger.ONE);
    }

    public String encryptVoteAndGenerateProof(int vote) {
        // set the vote modulus the prime number
        ModInteger votingMessage = new ModInteger(vote, this.publicKey.getP());

        // encrypt the message
        IHomomorphicEncryption<CipherText> encryption = new Encryption();
        CipherText cipherText = encryption.encrypt(this.publicKey, votingMessage);

        IMembershipProofFactory<MembershipProof> membershipProofFactory = new AdditiveElGamalMembershipProofFactory();
        MembershipProof proof = membershipProofFactory.createProof(
            this.publicKey.getP(),
            this.publicKey.getQ(),
            this.publicKey.getG(),
            this.publicKey.getH(),
            votingMessage,
            cipherText.getG(),
            cipherText.getH(),
            cipherText.getR()
        );


    }
}
