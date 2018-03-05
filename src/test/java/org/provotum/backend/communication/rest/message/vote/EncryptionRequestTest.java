package org.provotum.backend.communication.rest.message.vote;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.partial.Addresses;
import org.provotum.backend.communication.message.partial.Election;
import org.provotum.backend.communication.message.partial.PublicKey;
import org.provotum.backend.communication.rest.message.deployment.BallotDeploymentRequest;

public class EncryptionRequestTest extends TestCase {

    public void testAccessors() {
        Addresses addresses = new Addresses("zk");
        PublicKey pk = new PublicKey(1, 2);
        Election election = new Election("question", pk);

        BallotDeploymentRequest bdr = new BallotDeploymentRequest(addresses, election);

        assertEquals(addresses, bdr.getAddresses());
        assertEquals(election, bdr.getElection());
    }
}
