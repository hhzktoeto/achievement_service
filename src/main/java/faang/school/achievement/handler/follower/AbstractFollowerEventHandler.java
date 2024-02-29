package faang.school.achievement.handler.follower;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

@Component
@Slf4j
public abstract class AbstractFollowerEventHandler<T> implements EventHandler<T> {

    private ObjectMapper objectMapper;

    public void handleEvent(Message message, Class<T> type, Consumer<T> consumer) throws IOException {
        try {
            T event = objectMapper.readValue(message.getBody(), type);
            log.error("Successful convert data to Json");
            consumer.accept(event);
        } catch (IOException e) {
            log.error("Unsuccessful convert data to Json");
            throw new RuntimeException(e.getMessage());
        }
    }
}
