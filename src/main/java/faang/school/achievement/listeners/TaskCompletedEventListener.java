package faang.school.achievement.listeners;

import faang.school.achievement.dto.TaskCompletedEvent;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TaskCompletedEventListener extends AbstractEventListener<TaskCompletedEvent> {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            TaskCompletedEvent taskCompletedEvent = objectMapper.readValue(message.getBody(), TaskCompletedEvent.class);
            eventHandlers.stream()
                    .filter(eventHandler -> eventHandler.supportsEventType() == taskCompletedEvent.getClass())
                    .forEach(eventHandler -> eventHandler.handle(taskCompletedEvent.getUserId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
