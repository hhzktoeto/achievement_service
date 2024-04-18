package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.ProjectCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProjectCreateEventListener implements MessageListener {
    private ProjectCreateEvent projectCreateEvent;

    @Async
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("InviteEventListener has received a new message from Redis");
        try {
            String jsonMessage = new String((byte[]) message.getBody());
            ProjectCreateEvent projectViewEvent = new ObjectMapper().readValue(jsonMessage, ProjectCreateEvent.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON message");
            throw new SerializationException("Error parsing JSON message");
        }
    }
}
