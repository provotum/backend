package org.provotum.backend.ethereum.config;

public class BallotContractConfig {

    private String votingQuestion;
    private String zeroKnowledgeContractAddress;

    public BallotContractConfig(String votingQuestion, String zeroKnowledgeContractAddress) {
        this.votingQuestion = votingQuestion;
        this.zeroKnowledgeContractAddress = zeroKnowledgeContractAddress;
    }

    public String getVotingQuestion() {
        return votingQuestion;
    }

    public String getZeroKnowledgeContractAddress() {
        return zeroKnowledgeContractAddress;
    }
}
