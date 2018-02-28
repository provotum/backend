package org.provotum.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

@Configuration
@PropertySource("classpath:provotum-backend.properties")
public class EthereumConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${ethereum.wallet.location}")
    private String ethereumWalletLocation;

    @Value("${ethereum.wallet.password}")
    private String ethereumWalletPassword;

    public Credentials getWalletCredentials() {
        File wallet = new File(this.ethereumWalletLocation);

        if (! wallet.exists() || wallet.isDirectory()) {
            throw new IllegalArgumentException("Wallet does not exist or is a directory at path " + wallet.getAbsolutePath());
        }

        try {
            return WalletUtils.loadCredentials(this.ethereumWalletPassword, wallet);
        } catch (IOException | CipherException e) {
            throw new IllegalStateException("Failed to load credentials from path " + wallet.getAbsolutePath() + ": " + e.getMessage(), e);
        }
    }
}
