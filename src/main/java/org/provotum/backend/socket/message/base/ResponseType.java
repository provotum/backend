package org.provotum.backend.socket.message.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResponseType {

    @JsonProperty("zero-knowledge-deployed")
    ZERO_KNOWLEDGE_DEPLOYED,

    @JsonProperty("ballot-deployed")
    BALLOT_DEPLOYED,

    @JsonProperty("proof-event")
    PROOF_EVENT,

    @JsonProperty("change-event")
    CHANGE_EVENT,

    @JsonProperty("vote-event")
    VOTE_EVENT,

    @JsonProperty("open-vote")
    OPEN_VOTE,

    @JsonProperty("vote")
    VOTE,

    @JsonProperty("close-vote")
    CLOSE_VOTE

}
