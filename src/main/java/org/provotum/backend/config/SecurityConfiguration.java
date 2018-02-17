package org.provotum.backend.config;

import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.bouncycastle.jce.interfaces.ElGamalPublicKey;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.provotum.backend.ethereum.accessor.ZeroKnowledgeContractAccessor;
import org.provotum.security.elgamal.PrivateKey;
import org.provotum.security.elgamal.PublicKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:provotum-backend.properties")
public class SecurityConfiguration {

    private static final Logger logger = Logger.getLogger(SecurityConfiguration.class.getName());

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public SecurityConfiguration() throws InvalidAlgorithmParameterException {
        logger.info("Generating voting election keypair.");
        ElGamalParametersGenerator generator = new ElGamalParametersGenerator();
        generator.init(160, 20, new SecureRandom());
        ElGamalParameters parameters = generator.generateParameters();

        ElGamalParameterSpec elGamalParameterSpec = new ElGamalParameterSpec(parameters.getP(), parameters.getG());

        KeyPairGeneratorSpi keyPairGeneratorSpi = new org.bouncycastle.jcajce.provider.asymmetric.elgamal.KeyPairGeneratorSpi();
        keyPairGeneratorSpi.initialize(elGamalParameterSpec, new SecureRandom());

        KeyPair keyPair = keyPairGeneratorSpi.generateKeyPair();

        logger.info("Generated voting election keypair.");

        ElGamalPublicKey pubKey = (ElGamalPublicKey) keyPair.getPublic();
        ElGamalPrivateKey privKey = (ElGamalPrivateKey) keyPair.getPrivate();

        this.publicKey = new PublicKey(pubKey);
        this.privateKey = new PrivateKey(privKey);
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
