package org.provotum.backend.socket.controller;

import org.provotum.backend.socket.message.zeroknowledge.ZeroKnowledgeDeploymentRequest;
import org.provotum.backend.socket.message.zeroknowledge.ZeroKnowledgeDeploymentResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ZeroKnowledgeController {

    @MessageMapping("/contracts/zero-knowledge/deploy")
    @SendTo("/contracts/zero-knowledge/subscription/deployment")
    public ZeroKnowledgeDeploymentResponse deployBallot(ZeroKnowledgeDeploymentRequest request) {
        Map<String, Object> errorMessages = new HashMap<>();
        errorMessages.put("message", "This can be an error message");

        return new ZeroKnowledgeDeploymentResponse("0x123", errorMessages);
    }
}
