package faang.school.achievement.listener;

import faang.school.achievement.dto.GoalSetEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GoalEventListener extends AbstractEventListener<GoalSetEvent> {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        GoalSetEvent event = getEvent(message, GoalSetEvent.class);
        log.info("Start processing an incoming event - {}", event);
        processEvent(event.getUserId());
    }
}