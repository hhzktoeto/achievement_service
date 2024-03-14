package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public abstract class AbstractEventListener<T> implements MessageListener {
    protected ObjectMapper objectMapper;
    protected List<EventHandler<T>> eventHandlers;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setEventHandlers(List<EventHandler<T>> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    protected T getEvent(Message message, Class<T> eventTypeClass) {
        try {
            return objectMapper.readValue(message.getBody(), eventTypeClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processEvent(long userId) {
        eventHandlers.forEach(eventHandler -> eventHandler.handle(userId));
    }
}