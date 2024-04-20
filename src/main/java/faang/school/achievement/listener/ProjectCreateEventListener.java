package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.service.event.BusinessmanAchievementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProjectCreateEventListener implements MessageListener {
    private ObjectMapper objectMapper;
    private BusinessmanAchievementHandler businessmanAchievementHandler;

    @Async
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("InviteEventListener has received a new message from Redis");
        try {
            ProjectCreateEvent projectCreateEvent = objectMapper.readValue(message.getBody(), ProjectCreateEvent.class);
            businessmanAchievementHandler.handle(projectCreateEvent);
        } catch (IOException e) {
            log.error("Error parsing JSON message");
            throw new SerializationException("Error parsing JSON message");
        }
    }
}
