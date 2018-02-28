package org.provotum.backend.config;

import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.bouncycastle.jce.interfaces.ElGamalPublicKey;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.provotum.backend.timer.EvaluationTimer;
import org.provotum.security.elgamal.PrivateKey;
import org.provotum.security.elgamal.PublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.security.*;
import java.util.UUID;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:provotum-backend.properties")
public class SecurityConfiguration {

    private static final Logger logger = Logger.getLogger(SecurityConfiguration.class.getName());

    private static final int RSA_KEY_LENGTH = 1024;
    private static final int EL_GAMAL_KEY_LENGTH = 160;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private KeyPair rsaKeyPair;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    public SecurityConfiguration(EvaluationTimer timer) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        logger.info("Generating voting election keypair.");
        UUID uuid = timer.start();
        ElGamalParametersGenerator generator = new ElGamalParametersGenerator();
        generator.init(EL_GAMAL_KEY_LENGTH, 20, new SecureRandom());
        ElGamalParameters parameters = generator.generateParameters();

        ElGamalParameterSpec elGamalParameterSpec = new ElGamalParameterSpec(parameters.getP(), parameters.getG());

        KeyPairGeneratorSpi keyPairGeneratorSpi = new org.bouncycastle.jcajce.provider.asymmetric.elgamal.KeyPairGeneratorSpi();
        keyPairGeneratorSpi.initialize(elGamalParameterSpec, new SecureRandom());

        KeyPair keyPair = keyPairGeneratorSpi.generateKeyPair();

        EvaluationTimer.Duration duration = timer.end(uuid);
        timer.logDuration(EvaluationTimer.LogCategory.KEY_GENERATION_EL_GAMAL, duration);

        logger.info("Generated voting election keypair.");

        ElGamalPublicKey pubKey = (ElGamalPublicKey) keyPair.getPublic();
        ElGamalPrivateKey privKey = (ElGamalPrivateKey) keyPair.getPrivate();

        this.publicKey = new PublicKey(pubKey);
        this.privateKey = new PrivateKey(privKey);

        logger.info("Generating RSA encryption keypair.");
        uuid = timer.start();
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(RSA_KEY_LENGTH);
        this.rsaKeyPair = keyGen.generateKeyPair();
        duration = timer.end(uuid);
        timer.logDuration(EvaluationTimer.LogCategory.KEY_GENERATION_RSA, duration);
        logger.info("Generated RSA encryption keypair.");
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public KeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }
}
