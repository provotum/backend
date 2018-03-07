package org.provotum.backend.timer;

import org.provotum.backend.config.EvaluationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class EvaluationTimer {

    public enum LogCategory {
        DECRYPTION_CIPHERTEXT,
        DECRYPTION_RANDOM,
        DESERIALIZATION_CIPHERTEXT,
        DESERIALIZATION_PROOF,
        ENCRYPTION_CIPHERTEXT,
        ENCRYPTION_RANDOM,
        GENERATING_PROOF,
        KEY_GENERATION_EL_GAMAL,
        KEY_GENERATION_RSA,
        SERIALIZATION_CIPHERTEXT,
        SERIALIZATION_PROOF,
        SUM_PROOF,
        VERIFICATION_PROOF_SUCCESSFUL,
        VERIFICATION_PROOF_UNSUCCESSFUL
    }

    public class Duration {
        public long start;
        public long end;
        public long duration;
    }

    private Map<UUID, Duration> durations = new HashMap<>();
    private FileOutputStream output;

    @Autowired
    public EvaluationTimer(EvaluationConfiguration evaluationConfiguration) throws IOException {
        this.output = new FileOutputStream(evaluationConfiguration.getOutputFile(), true);
    }

    public UUID start() {
        Duration d = new Duration();
        d.start = System.nanoTime();

        UUID uuid = UUID.randomUUID();

        this.durations.put(uuid, d);
        return uuid;
    }

    public Duration end(UUID identifier) {
        Duration d = this.durations.get(identifier);
        d.end = System.nanoTime();
        d.duration = d.end - d.start;

        return d;
    }

    public void logDuration(LogCategory category, Duration duration) {
        try {
            this.output.write(("[" + category.name() + "] " + Long.toString(duration.duration) + "ns (Seconds: " + (double) duration.duration/ 1000000000.0 + "s)\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
