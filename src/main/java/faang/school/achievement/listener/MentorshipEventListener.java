package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MentorshipEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler> eventHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            objectMapper.findAndRegisterModules();
            MentorshipStartEvent event = objectMapper.readValue(message.getBody(), MentorshipStartEvent.class);
            // передает всем обработчикам достижений связанных с подпиской
            eventHandlers.forEach(handler -> handler.handle(event.getMentorId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
