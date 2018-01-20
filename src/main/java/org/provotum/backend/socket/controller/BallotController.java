package org.provotum.backend.socket.controller;

import org.provotum.backend.ethereum.accessor.BallotContractAccessor;
import org.provotum.backend.ethereum.config.BallotContractConfig;
import org.provotum.backend.ethereum.wrappers.Ballot;
import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.deployment.BallotDeploymentRequest;
import org.provotum.backend.socket.message.deployment.BallotDeploymentResponse;
import org.provotum.backend.socket.message.partial.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class BallotController {

    private static final Logger logger = Logger.getLogger(BallotController.class.getName());

    private BallotContractAccessor ballotContractAccessor;

    @Autowired
    public BallotController(BallotContractAccessor ballotContractAccessor) {
        this.ballotContractAccessor = ballotContractAccessor;
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
}
