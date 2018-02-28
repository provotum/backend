package org.provotum.backend.communication.rest.controller;

import org.provotum.backend.communication.rest.message.vote.EncryptionRequest;
import org.provotum.backend.communication.rest.message.vote.EncryptionResponse;
import org.provotum.backend.communication.rest.message.vote.VerifyProofRequest;
import org.provotum.backend.security.CipherTextWrapper;
import org.provotum.backend.security.EncryptionManager;
import org.provotum.backend.timer.EvaluationTimer;
import org.provotum.security.elgamal.additive.CipherText;
import org.provotum.security.elgamal.proof.noninteractive.MembershipProof;
import org.provotum.security.serializer.CipherTextSerializer;
import org.provotum.security.serializer.MembershipProofSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class EncryptionController {

    private static final Logger logger = Logger.getLogger(EncryptionController.class.getName());
    private static final String CONTEXT = "/encryption";

    private EncryptionManager encryptionManager;
    private EvaluationTimer timer;

    @Autowired
    public EncryptionController(EncryptionManager encryptionManager, EvaluationTimer timer) {
        this.encryptionManager = encryptionManager;
        this.timer = timer;
    }

    @RequestMapping(value = CONTEXT + "/generate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public EncryptionResponse encryptAndGenerateProof(@RequestBody EncryptionRequest encryptionRequest) {
        logger.info("Received request to encrypt and generate vote.");

        CipherTextWrapper cipherTextWrapper = null;
        try {
            cipherTextWrapper = this.encryptionManager.encryptVoteAndGenerateProof(encryptionRequest.getVote());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            logger.severe("Failed to generate proof: " + e.getMessage());
            e.printStackTrace();

            return null;
        }

        return new EncryptionResponse(cipherTextWrapper.getCiphertext(), cipherTextWrapper.getProof(), cipherTextWrapper.getRandom());
    }

    @RequestMapping(value = CONTEXT + "/verify", method = RequestMethod.POST)
    public ResponseEntity verifyProof(@RequestBody VerifyProofRequest proofRequest) {
        logger.info("Received request to verify proof.");

        CipherText cipherText = CipherTextSerializer.fromString(proofRequest.getCiphertext());
        MembershipProof proof = MembershipProofSerializer.fromString(proofRequest.getProof());

        try {
            boolean isProven = this.encryptionManager.verifyProof(cipherText, proof);

            if (isProven) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        } catch (Exception e) {
            logger.severe("Failed to verify proof: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
