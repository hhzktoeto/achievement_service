package faang.school.achievement.listener.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.follower.FollowerEvent;
import faang.school.achievement.service.event.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowerEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler<FollowerEvent>> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("Recieved follower event: {}", message);
            FollowerEvent followerEvent = objectMapper.readValue(message.getBody(), FollowerEvent.class);
            handlers.forEach(handler -> handler.handle(followerEvent));
        } catch (IOException e) {
            log.error("IOException was thrown", e);
            throw new SerializationException("Failed to deserialize message", e);
        }
    }
}
