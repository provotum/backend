package org.provotum.backend.communication.rest.message.deployment;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.partial.PublicKey;

public class ZeroKnowledgeDeploymentTest extends TestCase {

    public void testAccessors() {
        PublicKey pk = new PublicKey(1, 2);
        ZeroKnowledgeDeploymentRequest zkr = new ZeroKnowledgeDeploymentRequest(pk);

        assertEquals(pk, zkr.getPublicKey());
    }
}
