package org.provotum.backend.communication.rest.message.deployment;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.partial.Addresses;
import org.provotum.backend.communication.message.partial.Election;
import org.provotum.backend.communication.message.partial.PublicKey;

public class BallotDeploymentRequestTest extends TestCase {

    public void testAccessors() {
        Addresses addresses = new Addresses("zk");
        PublicKey pk = new PublicKey(1, 2);
        Election election = new Election("question", pk);

        BallotDeploymentRequest br = new BallotDeploymentRequest(addresses, election);

        assertEquals(election, br.getElection());
        assertEquals(addresses, br.getAddresses());
    }
}
