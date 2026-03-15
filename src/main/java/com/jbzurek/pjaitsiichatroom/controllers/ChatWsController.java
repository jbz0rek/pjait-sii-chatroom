package com.jbzurek.pjaitsiichatroom.controllers;

import com.jbzurek.pjaitsiichatroom.domain.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWsController {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    private final String topic;

    public ChatWsController(
            KafkaTemplate<String, MessageDto> kafkaTemplate,
            @Value("${chat.kafka.topic}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @MessageMapping("/chat")
    public void send(MessageDto message) {
        kafkaTemplate.send(topic, message);
    }
}
