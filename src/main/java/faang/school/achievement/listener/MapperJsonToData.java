package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class MapperJsonToData {

    private final ObjectMapper objectMapper;

    public <T> T convertJsonToEvent(Message message, Class<T> classType) {
        try {
            return objectMapper.readValue(message.getBody(), classType);
        } catch (IOException e) {
            log.error("Unsuccessful convert Json to Data");
            throw new RuntimeException(e.getMessage());
        }
    }

}
