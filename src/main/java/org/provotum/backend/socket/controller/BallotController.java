package org.provotum.backend.socket.controller;

import org.provotum.backend.socket.message.ballot.BallotDeploymentRequest;
import org.provotum.backend.socket.message.ballot.BallotDeploymentResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BallotController {

    @MessageMapping("/contracts/ballot/deploy")
    @SendTo("/contracts/ballot/subscription/deployment")
    public BallotDeploymentResponse deployBallot(BallotDeploymentRequest request) {
        Map<String, Object> errorMessages = new HashMap<>();
        errorMessages.put("message", "This can be an error message");

        return new BallotDeploymentResponse("0x123", errorMessages);
    }
}
