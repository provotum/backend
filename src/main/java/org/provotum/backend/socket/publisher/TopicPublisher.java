package org.provotum.backend.socket.publisher;

import org.provotum.backend.socket.message.base.AResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class TopicPublisher {

    private static final Logger logger = Logger.getLogger(TopicPublisher.class.getName());
    private final SimpMessagingTemplate messageTemplate;

    @Autowired
    public TopicPublisher(SimpMessagingTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public void send(String topic, AResponse response) {
        logger.info("Sending response with id " + response.getId() + " to topic " + topic);
        this.messageTemplate.convertAndSend(topic, response);
    }

}
