package org.snoozy.snoozyauth.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.snoozy.snoozyauth.producer.event.GoogleAuthEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static org.snoozy.snoozyauth.config.KafkaConfig.EVENT_TYPE;
import static org.snoozy.snoozyauth.config.KafkaConfig.TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleAuthProducer {
    private final KafkaTemplate<String, GoogleAuthEvent> kafkaTemplate;

    public void sendGoogleAuthEvent(GoogleAuthEvent event) {
        kafkaTemplate.send(TOPIC, EVENT_TYPE, event);
        log.info("Authenticated user: {}", event);
    }
}
