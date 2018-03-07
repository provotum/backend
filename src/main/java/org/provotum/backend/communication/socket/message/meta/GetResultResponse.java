package org.provotum.backend.communication.socket.message.meta;

import org.provotum.backend.communication.message.base.AResponse;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class GetResultResponse extends AResponse {

    private Map<String, BigInteger> votes = new HashMap<>();
    private final ResponseType responseType = ResponseType.GET_RESULTS_EVENT;

    public GetResultResponse(String id, Status status, String message, BigInteger yes, BigInteger no, BigInteger total) {
        super(id, status, message);

        votes.put("yes", yes);
        votes.put("no", no);
        votes.put("total", total);
    }

    public GetResultResponse(Status status, String message, BigInteger yes, BigInteger no, BigInteger total) {
        super(status, message);

        votes.put("yes", yes);
        votes.put("no", no);
        votes.put("total", total);
    }

    public Map<String, BigInteger> getVotes() {
        return votes;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
