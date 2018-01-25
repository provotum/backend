package org.provotum.backend.communication.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // enable an in-memory message broker, sending messages
        // to clients with an prefix of topic.
        config.enableSimpleBroker("/topic");

        // configure the prefix which is appended to the routes
        // on which websocket controllers are listening on.
        config.setApplicationDestinationPrefixes("/websocket");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // register endpoint for SockJS, enabling it to use different
        // endpoints if a browser does not support WebSockets.
        registry
                .addEndpoint("/sockjs-websocket")
                .setAllowedOrigins("*") // allow request to come from everywhere...
                .withSockJS();
    }
}
