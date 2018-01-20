package org.provotum.backend.socket.controller;

import org.provotum.backend.ethereum.accessor.ZeroKnowledgeContractAccessor;
import org.provotum.backend.ethereum.config.ZeroKnowledgeContractConfig;
import org.provotum.backend.ethereum.wrappers.ZeroKnowledgeVerificator;
import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.deployment.ZeroKnowledgeDeploymentRequest;
import org.provotum.backend.socket.message.deployment.ZeroKnowledgeDeploymentResponse;
import org.provotum.backend.socket.message.partial.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class ZeroKnowledgeController {

    private static final Logger logger = Logger.getLogger(ZeroKnowledgeController.class.getName());

    private ZeroKnowledgeContractAccessor zkAccessor;

    @Autowired
    public ZeroKnowledgeController(ZeroKnowledgeContractAccessor zkAccessor) {
        this.zkAccessor = zkAccessor;
    }

    @MessageMapping("/contracts/zero-knowledge/deploy")
    @SendTo("/topic/deployments")
    public ZeroKnowledgeDeploymentResponse deployBallot(ZeroKnowledgeDeploymentRequest request) {
        logger.info("Received zero-knowledge deployment request");

        ZeroKnowledgeVerificator zkVerificator = null;

        try {
            zkVerificator = zkAccessor.deploy(new ZeroKnowledgeContractConfig());
        } catch (Exception e) {
            logger.severe("Failed to deploy zero-knowledge verificator: " + e.getMessage());

            return new ZeroKnowledgeDeploymentResponse(Status.ERROR, "Deployment failed: " + e.getMessage(), new Contract("zero-knowledge", null));
        }

        return new ZeroKnowledgeDeploymentResponse(Status.SUCCESS, "Deployment successful", new Contract("zero-knowledge", zkVerificator.getContractAddress()));
    }
}
