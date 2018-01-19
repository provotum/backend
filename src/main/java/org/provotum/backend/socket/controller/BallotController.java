package org.provotum.backend.socket.controller;

import org.provotum.backend.socket.message.ballot.BallotDeploymentRequest;
import org.provotum.backend.socket.message.ballot.BallotDeploymentResponse;
import org.provotum.backend.socket.message.contract.Contract;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BallotController {

    @MessageMapping("/contracts/ballot/deploy")
    @SendTo("/topic/deployments")
    public BallotDeploymentResponse deployBallot(BallotDeploymentRequest request) {
        return new BallotDeploymentResponse("success", "Deployment successful", new Contract("ballot", "0x123"));
    }
}
