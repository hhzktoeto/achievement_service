package faang.school.achievement.listeners;

import faang.school.achievement.dto.TaskCompletedEvent;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
public class TaskCompletedEventListener extends AbstractEventListener<TaskCompletedEvent> {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        TaskCompletedEvent taskCompletedEvent = getEvent(message.getBody(), TaskCompletedEvent.class);
        processEvent(taskCompletedEvent.getClass(), taskCompletedEvent.getUserId());
    }
}
