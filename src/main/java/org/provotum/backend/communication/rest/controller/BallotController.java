package org.provotum.backend.communication.rest.controller;

import org.provotum.backend.communication.rest.message.deployment.BallotDeploymentRequest;
import org.provotum.backend.communication.rest.message.vote.VoteRequest;
import org.provotum.backend.config.EthereumConfiguration;
import org.provotum.backend.ethereum.accessor.BallotContractAccessor;
import org.provotum.backend.ethereum.config.BallotContractConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;

import java.util.logging.Logger;

@RestController
public class BallotController {

    private static final Logger logger = Logger.getLogger(BallotController.class.getName());
    private static final String CONTEXT = "/ballot";

    private BallotContractAccessor ballotContractAccessor;
    private EthereumConfiguration ethereumConfiguration;

    @Autowired
    public BallotController(BallotContractAccessor ballotContractAccessor, EthereumConfiguration ethereumConfiguration) {
        this.ballotContractAccessor = ballotContractAccessor;
        this.ethereumConfiguration = ethereumConfiguration;
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

    @RequestMapping(value = CONTEXT + "/{contractAddress}/vote", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void vote(@PathVariable String contractAddress, @RequestBody VoteRequest voteRequest) {
        logger.info("Received vote request");

//        Credentials credentials = Credentials.create(privateKey, publicKey);
        Credentials credentials = this.ethereumConfiguration.getVoterCredentials();

        // TODO: remove this log statement!
        logger.info("PublicKey: " + credentials.getEcKeyPair().getPublicKey() + ", PrivateKey: " + credentials.getEcKeyPair().getPrivateKey());

        this.ballotContractAccessor.vote(
            contractAddress,
            voteRequest.getVote(),
            credentials
        );
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
