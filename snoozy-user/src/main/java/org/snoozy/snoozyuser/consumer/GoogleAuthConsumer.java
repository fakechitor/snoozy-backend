package org.snoozy.snoozyuser.consumer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.snoozy.snoozyuser.consumer.event.GoogleAuthMessage;
import org.snoozy.snoozyuser.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class GoogleAuthConsumer {
    public final String TOPIC = "AUTHENTICATION";

    private final UserService userService;

    @KafkaListener(topics = TOPIC)
    public void receiveRegistrationEvent(@Payload GoogleAuthMessage authMessage) {
        log.info("Received Google Auth Event, {}", authMessage);
        userService.handleAuthEvent(authMessage);
    }
}
