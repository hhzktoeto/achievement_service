package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AbstractEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractEventListener<T extends AbstractEvent> implements MessageListener {
    private final Class<T> eventType;
    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> goalAchievementHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T event = objectMapper.readValue(message.getBody(), eventType);
            goalAchievementHandlers.forEach(handler -> handler.handle(event.getUserId()));
        } catch (IOException e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
