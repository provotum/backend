package org.provotum.backend.communication.socket.message.meta;

import org.provotum.backend.communication.message.base.AResponse;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

import java.math.BigInteger;
import java.util.Map;

public class GetResultResponse extends AResponse {

    private Map<String, BigInteger> votes;
    private final ResponseType responseType = ResponseType.GET_RESULTS_EVENT;

    public GetResultResponse(String id, Status status, String message, Map<String, BigInteger> votes) {
        super(id, status, message);
        this.votes = votes;
    }

    public GetResultResponse(Status status, String message, Map<String, BigInteger> votes) {
        super(status, message);
        this.votes = votes;
    }

    public Map<String, BigInteger> getVotes() {
        return votes;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
