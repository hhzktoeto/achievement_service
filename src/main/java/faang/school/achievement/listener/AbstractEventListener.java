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
            objectMapper.findAndRegisterModules(); // без этой строчки ругается что нет зависимости jackson-datatype-jsr310
            // хотя сама зависимость есть, другого варианта как пофиксить не нашел
            T event = objectMapper.readValue(message.getBody(), eventType);
            processEvent(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void processEvent(T event);
}
