package faang.school.achievement.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

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
}
