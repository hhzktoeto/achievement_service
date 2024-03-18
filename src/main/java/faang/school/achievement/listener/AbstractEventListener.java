package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T> {

    protected final List<EventHandler<T>> eventHandlers;

    protected void handle(Message message, Class<T> type) {
        T event = mapToEvent(message, type);
        eventHandlers.forEach(eventHandler -> eventHandler.handle(event));
    }

    private T mapToEvent(Message message, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(message.getBody(), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
