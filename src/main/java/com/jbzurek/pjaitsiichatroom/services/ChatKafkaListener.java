package com.jbzurek.pjaitsiichatroom.services;

import com.jbzurek.pjaitsiichatroom.domain.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(ChatKafkaListener.class);

    private final SimpMessagingTemplate ws;
    private final MessageService messageService;

    public ChatKafkaListener(SimpMessagingTemplate ws, MessageService messageService) {
        this.ws = ws;
        this.messageService = messageService;
    }

    @KafkaListener(
            topics = "${chat.kafka.topic}",
            groupId = "${chat.kafka.group-id}"
    )
    public void listen(MessageDto msg) {
        log.info("received message from kafka: {}", msg);

        messageService.addMessageWithNormalization(msg);
        ws.convertAndSend("/topic/greetings", msg);
    }
}
