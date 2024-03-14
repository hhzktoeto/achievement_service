package faang.school.achievement.listener;

import faang.school.achievement.dto.TaskCompletedEvent;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
public class TaskCompletedEventListener extends AbstractEventListener<TaskCompletedEvent> {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        TaskCompletedEvent taskCompletedEvent = getEvent(message, TaskCompletedEvent.class);
        processEvent(taskCompletedEvent.getUserId());
    }
}