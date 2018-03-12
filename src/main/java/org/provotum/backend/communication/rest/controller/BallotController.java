package org.provotum.backend.communication.rest.controller;

import org.provotum.backend.communication.rest.message.deployment.BallotDeploymentRequest;
import org.provotum.backend.ethereum.accessor.BallotContractAccessor;
import org.provotum.backend.ethereum.config.BallotContractConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class BallotController {

    private static final Logger logger = Logger.getLogger(BallotController.class.getName());
    private static final String CONTEXT = "/ballot";

    private BallotContractAccessor ballotContractAccessor;

    @Autowired
    public BallotController(BallotContractAccessor ballotContractAccessor) {
        this.ballotContractAccessor = ballotContractAccessor;
    }

    @RequestMapping(value = CONTEXT + "/deploy", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deployBallot(@RequestBody BallotDeploymentRequest request) {
        logger.info("Received ballot deployment request");

        // TODO: validate request for required fields

        this.ballotContractAccessor.deploy(
            new BallotContractConfig(
                request.getElection().getQuestion(),
                request.getAddresses().getZeroKnowledge())
        );
    }

    @RequestMapping(value = CONTEXT + "/address", method = RequestMethod.GET)
    public ResponseEntity getBallotContractAddress() {
        Map<String, String> resultMap = new HashMap<>();

        if (null == this.ballotContractAccessor.getBallotAddress()) {
            resultMap.put("address", null);

            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(resultMap);
        }

        resultMap.put("address", this.ballotContractAccessor.getBallotAddress());

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

    @RequestMapping(value = CONTEXT + "/{contractAddress}/open-vote", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void openVote(@PathVariable String contractAddress) {
        logger.info("Received open vote request");

        this.ballotContractAccessor.openVoting(contractAddress);
    }

    @RequestMapping(value = CONTEXT + "/{contractAddress}/close-vote", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void closeVote(@PathVariable String contractAddress) {
        logger.info("Received close vote request");

        this.ballotContractAccessor.closeVoting(contractAddress);
    }

    @RequestMapping(value = CONTEXT + "/{contractAddress}/remove", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeBallot(@PathVariable String contractAddress) {
        logger.info("Received remove ballot contract request");

        this.ballotContractAccessor.remove(contractAddress);
    }

    @RequestMapping(value = CONTEXT + "/{contractAddress}/question", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void requestVotingQuestion(@PathVariable String contractAddress) {
        logger.info("Received ballot contract voting question request");

        this.ballotContractAccessor.getQuestion(contractAddress);
    }

    @RequestMapping(value = CONTEXT + "/{contractAddress}/results", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void requestVotingResults(@PathVariable String contractAddress) {
        logger.info("Received ballot contract voting result request");

        this.ballotContractAccessor.getResults(contractAddress);
    }
}
