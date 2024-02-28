package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

public abstract class AbstractEventListener<T> implements MessageListener {
    @Autowired
    private ObjectMapper objectMapper;
    private final Class<T> eventType;

    public AbstractEventListener(Class<T> eventType) {
        this.eventType = eventType;
    }

    private void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            objectMapper.findAndRegisterModules();
            T event = objectMapper.readValue(message.getBody(), eventType);
            processEvent(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void processEvent(T event);
}
