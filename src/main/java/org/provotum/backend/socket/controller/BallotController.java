package org.provotum.backend.socket.controller;

import org.provotum.backend.socket.message.ballot.BallotDeploymentRequest;
import org.provotum.backend.socket.message.ballot.BallotDeploymentResponse;
import org.provotum.backend.socket.message.contract.Contract;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class BallotController {

    private static final Logger logger = Logger.getLogger(BallotController.class.getName());

    @MessageMapping("/contracts/ballot/deploy")
    @SendTo("/topic/deployments")
    public BallotDeploymentResponse deployBallot(BallotDeploymentRequest request) {
        logger.info("Received ballot deployment request");

        return new BallotDeploymentResponse("success", "Deployment successful", new Contract("ballot", "0x123"));
    }
}
