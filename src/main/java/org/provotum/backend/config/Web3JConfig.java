package org.provotum.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:provotum-backend.properties")
public class Web3JConfig {

    private static final Logger logger = Logger.getLogger(Web3JConfig.class.getName());

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${ethereum.web3j.rpchost}")
    private String rpcHost;

    @Bean
    public Web3j web3j() {
        logger.info("Configuring Web3J to connect to RPC host at " + this.rpcHost);

        if (! "http".equals(this.rpcHost.substring(0, 4))) {
            throw new IllegalArgumentException("The RPC host for Web3J must start with either https or http. Got " + this.rpcHost);
        }

        return Web3j.build(new HttpService(this.rpcHost));
    }
}
