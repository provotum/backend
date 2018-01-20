package org.provotum.backend.socket.controller;

import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.deployment.BallotDeploymentRequest;
import org.provotum.backend.socket.message.deployment.BallotDeploymentResponse;
import org.provotum.backend.socket.message.partial.contract.Contract;
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

        return new BallotDeploymentResponse(Status.SUCCESS, "Deployment successful", new Contract("ballot", "0x123"));
    }
}
