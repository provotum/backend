package org.provotum.backend.socket.controller;

import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.deployment.ZeroKnowledgeDeploymentRequest;
import org.provotum.backend.socket.message.deployment.ZeroKnowledgeDeploymentResponse;
import org.provotum.backend.socket.message.partial.contract.Contract;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class ZeroKnowledgeController {

    private static final Logger logger = Logger.getLogger(ZeroKnowledgeController.class.getName());

    @MessageMapping("/contracts/zero-knowledge/deploy")
    @SendTo("/topic/deployments")
    public ZeroKnowledgeDeploymentResponse deployBallot(ZeroKnowledgeDeploymentRequest request) {
        logger.info("Received zero-knowledge deployment request");

        return new ZeroKnowledgeDeploymentResponse(Status.SUCCESS, "Deployment successful", new Contract("zero-knowledge", "0x123"));
    }
}
