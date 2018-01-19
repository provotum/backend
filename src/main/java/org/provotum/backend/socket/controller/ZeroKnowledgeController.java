package org.provotum.backend.socket.controller;

import org.provotum.backend.socket.message.contract.Contract;
import org.provotum.backend.socket.message.zeroknowledge.ZeroKnowledgeDeploymentRequest;
import org.provotum.backend.socket.message.zeroknowledge.ZeroKnowledgeDeploymentResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ZeroKnowledgeController {

    @MessageMapping("/contracts/zero-knowledge/deploy")
    @SendTo("/topic/deployments")
    public ZeroKnowledgeDeploymentResponse deployBallot(ZeroKnowledgeDeploymentRequest request) {
        return new ZeroKnowledgeDeploymentResponse("success", "Deployment successful", new Contract("zero-knowledge", "0x123"));
    }
}
