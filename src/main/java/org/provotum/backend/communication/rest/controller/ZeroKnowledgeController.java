package org.provotum.backend.communication.rest.controller;

import org.provotum.backend.communication.rest.message.deployment.ZeroKnowledgeDeploymentRequest;
import org.provotum.backend.ethereum.accessor.ZeroKnowledgeContractAccessor;
import org.provotum.backend.ethereum.config.ZeroKnowledgeContractConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class ZeroKnowledgeController {

    private static final Logger logger = Logger.getLogger(ZeroKnowledgeController.class.getName());
    private static final String CONTEXT = "/zero-knowledge";

    private ZeroKnowledgeContractAccessor zeroKnowledgeContractAccessor;

    @Autowired
    public ZeroKnowledgeController(ZeroKnowledgeContractAccessor zeroKnowledgeContractAccessor) {
        this.zeroKnowledgeContractAccessor = zeroKnowledgeContractAccessor;
    }

    @RequestMapping(value = CONTEXT + "/deploy", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deployZeroKnowledge(@RequestBody ZeroKnowledgeDeploymentRequest request) {
        logger.info("Received zero-knowledge deployment request");
        this.zeroKnowledgeContractAccessor.deploy(new ZeroKnowledgeContractConfig());
    }

    @RequestMapping(value = CONTEXT + "/{contractAddress}/remove", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeZeroKnowledge(@PathVariable String contractAddress) {
        logger.info("Received zero-knowledge contract removal request");

        this.zeroKnowledgeContractAccessor.remove(contractAddress);
    }
}
