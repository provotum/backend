package org.provotum.backend.config;

import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.bouncycastle.jce.interfaces.ElGamalPublicKey;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.provotum.backend.timer.EvaluationTimer;
import org.provotum.security.elgamal.PrivateKey;
import org.provotum.security.elgamal.PublicKey;
import org.provotum.security.serializer.KeyPairSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:provotum-backend.properties")
public class SecurityConfiguration {

    private static final Logger logger = Logger.getLogger(SecurityConfiguration.class.getName());

    private static final int RSA_KEY_LENGTH = 1024;
    private static final int EL_GAMAL_KEY_LENGTH = 160;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${security.output.electionpublickey}")
    private String publicKeyStoragePath;

    @Value("${security.output.electionprivatekey}")
    private String privateKeyStoragePath;

    @Value("${security.output.rsapublickey}")
    private String rsaPublicKeyPairStoragePath;

    @Value("${security.output.rsaprivatekey}")
    private String rsaPrivateKeyPairStoragePath;

    EvaluationTimer timer;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private RSAPublicKey rsaPublicKey;
    private RSAPrivateKey rsaPrivateKey;

    @Autowired
    public SecurityConfiguration(EvaluationTimer timer) {
        this.timer = timer;
    }

    public void initializeKeys() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        if (null == this.publicKeyStoragePath) {
            throw new IllegalArgumentException("Missing or invalid configuration for security.output.election-publickey");
        }

        if (null == this.privateKeyStoragePath) {
            throw new IllegalArgumentException("Missing or invalid configuration for security.output.election-privatekey");
        }

        if (null == this.rsaPublicKeyPairStoragePath) {
            throw new IllegalArgumentException("Missing or invalid configuration for security.output.rsa-publickey");
        }

        if (null == this.rsaPrivateKeyPairStoragePath) {
            throw new IllegalArgumentException("Missing or invalid configuration for security.output.rsa-privatekey");
        }

        File publicKeyFile = new File(this.publicKeyStoragePath);
        File privateKeyFile = new File(this.privateKeyStoragePath);
        File rsaPublicKeyFile = new File(this.rsaPublicKeyPairStoragePath);
        File rsaPrivateKeyFile = new File(this.rsaPrivateKeyPairStoragePath);

        if (privateKeyFile.exists() && publicKeyFile.exists() && rsaPublicKeyFile.exists() && rsaPrivateKeyFile.exists()) {
            logger.info("Reading election keypair from storage...");

            FileInputStream publicKeyInput = new FileInputStream(publicKeyFile);
            byte[] publicKeyData = new byte[(int) publicKeyFile.length()];
            publicKeyInput.read(publicKeyData);
            publicKeyInput.close();
            this.publicKey = KeyPairSerializer.publicKeyFromString(new String(publicKeyData, StandardCharsets.UTF_8));

            FileInputStream privateKeyInput = new FileInputStream(privateKeyFile);
            byte[] data = new byte[(int) privateKeyFile.length()];
            privateKeyInput.read(data);
            privateKeyInput.close();
            this.privateKey = KeyPairSerializer.privateKeyFromString(new String(data, StandardCharsets.UTF_8));

            logger.info("Reading RSA keypair from storage...");

            FileInputStream rsaPublicKeyInput = new FileInputStream(rsaPublicKeyFile);
            byte[] rsaPublicKeyData = new byte[(int) rsaPublicKeyFile.length()];
            rsaPublicKeyInput.read(rsaPublicKeyData);
            rsaPublicKeyInput.close();
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(rsaPublicKeyData);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.rsaPublicKey = (RSAPublicKey) kf.generatePublic(publicKeySpec);

            FileInputStream rsaPrivateKeyInput = new FileInputStream(rsaPrivateKeyFile);
            byte[] rsaPrivateKeyData = new byte[(int) rsaPrivateKeyFile.length()];
            rsaPrivateKeyInput.read(rsaPrivateKeyData);
            rsaPrivateKeyInput.close();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKeyData);
            KeyFactory pkf = KeyFactory.getInstance("RSA");
            this.rsaPrivateKey = (RSAPrivateKey) pkf.generatePrivate(privateKeySpec);
        } else {
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

            logger.info("Writing election key pair...");

            FileOutputStream publicKeyOutput = new FileOutputStream(publicKeyFile);
            publicKeyOutput.write(KeyPairSerializer.serializePublicKey(this.publicKey).getBytes(StandardCharsets.UTF_8));
            publicKeyOutput.flush();
            publicKeyOutput.close();

            FileOutputStream privateKeyOutput = new FileOutputStream(privateKeyFile);
            privateKeyOutput.write(KeyPairSerializer.serializePrivateKey(this.privateKey).getBytes(StandardCharsets.UTF_8));
            privateKeyOutput.flush();
            privateKeyOutput.close();

            logger.info("Generating RSA encryption keypair.");
            uuid = timer.start();
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(RSA_KEY_LENGTH);
            KeyPair rsaKeyPair = keyGen.generateKeyPair();

            this.rsaPublicKey = (RSAPublicKey) rsaKeyPair.getPublic();
            this.rsaPrivateKey = (RSAPrivateKey) rsaKeyPair.getPrivate();

            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.rsaPublicKey.getEncoded());
            byte[] rsaPublicKeyBytes = x509EncodedKeySpec.getEncoded();
            FileOutputStream rsaPublicOutput = new FileOutputStream(rsaPublicKeyFile);
            rsaPublicOutput.write(rsaPublicKeyBytes);
            rsaPublicOutput.flush();
            rsaPublicOutput.close();

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(this.rsaPrivateKey.getEncoded());
            byte[] rsaPrivateKeyBytes = pkcs8EncodedKeySpec.getEncoded();
            FileOutputStream rsaPrivateOutput = new FileOutputStream(rsaPrivateKeyFile);
            rsaPrivateOutput.write(rsaPrivateKeyBytes);
            rsaPrivateOutput.flush();
            rsaPrivateOutput.close();

            duration = timer.end(uuid);
            timer.logDuration(EvaluationTimer.LogCategory.KEY_GENERATION_RSA, duration);
            logger.info("Generated RSA encryption keypair.");
        }
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public KeyPair getRsaKeyPair() {
        return new KeyPair(this.rsaPublicKey, this.rsaPrivateKey);
    }
}
