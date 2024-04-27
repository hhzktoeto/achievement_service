package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.service.event.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AbstractEventListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> handlers;
    private final Class<T> eventType;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("Recieved and event: {}", message);
            T event = objectMapper.readValue(message.getBody(), eventType);
            handlers.forEach(handler -> handler.handle(event));
        } catch (IOException e) {
            log.error("IOException was thrown", e);
            throw new SerializationException("Failed to deserialize message", e);
        }
    }
}
