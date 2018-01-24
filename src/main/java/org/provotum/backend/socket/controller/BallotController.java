package org.provotum.backend.socket.controller;

import org.provotum.backend.config.EthereumConfiguration;
import org.provotum.backend.ethereum.accessor.BallotContractAccessor;
import org.provotum.backend.ethereum.config.BallotContractConfig;
import org.provotum.backend.ethereum.wrappers.Ballot;
import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.deployment.BallotDeploymentRequest;
import org.provotum.backend.socket.message.deployment.BallotDeploymentResponse;
import org.provotum.backend.socket.message.partial.Contract;
import org.provotum.backend.socket.message.vote.VoteRequest;
import org.provotum.backend.socket.message.vote.VoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.web3j.crypto.Credentials;

import java.util.logging.Logger;

@Controller
public class BallotController {

    private static final Logger logger = Logger.getLogger(BallotController.class.getName());

    private BallotContractAccessor ballotContractAccessor;
    private EthereumConfiguration ethereumConfiguration;

    @Autowired
    public BallotController(BallotContractAccessor ballotContractAccessor, EthereumConfiguration ethereumConfiguration) {
        this.ballotContractAccessor = ballotContractAccessor;
        this.ethereumConfiguration = ethereumConfiguration;
    }

    @MessageMapping("/contracts/ballot/deploy")
    @SendTo("/topic/deployments")
    public BallotDeploymentResponse deployBallot(BallotDeploymentRequest request) {
        logger.info("Received ballot deployment request");

        Ballot ballot = null;
        try {
            ballot = this.ballotContractAccessor.deploy(
                new BallotContractConfig(
                    request.getElection().getQuestion(),
                    request.getAddresses().getZeroKnowledge())
            );
        } catch (Exception e) {
            logger.severe("Failed to deploy ballot: " + e.getMessage());

            return new BallotDeploymentResponse(Status.ERROR, "Deployment failed: " + e.getMessage(), new Contract("ballot", null));
        }

        return new BallotDeploymentResponse(Status.SUCCESS, "Deployment successful", new Contract("ballot", ballot.getContractAddress()));
    }

    @MessageMapping("/contracts/ballot/vote")
    @SendTo("/topic/vote")
    public VoteResponse vote(VoteRequest voteRequest) {
        logger.info("Received vote request");

//        Credentials credentials = Credentials.create(privateKey, publicKey);
        Credentials credentials = this.ethereumConfiguration.getVoterCredentials();

        // TODO: remove this log statement!
        logger.info("PublicKey: " + credentials.getEcKeyPair().getPublicKey() + ", PrivateKey: " + credentials.getEcKeyPair().getPrivateKey());

        String trx = null;
        try {
            trx = this.ballotContractAccessor.vote(
                voteRequest.getContractAddress(),
                voteRequest.getVote(),
                credentials
            );
        } catch (Exception e) {
            logger.severe("Failed to submit vote: " + e.getMessage());

            return new VoteResponse(Status.ERROR, "Voting failed: " + e.getMessage(), null);
        }

        return new VoteResponse(Status.SUCCESS, "Voting successful", trx);
    }
}
