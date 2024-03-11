package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSetEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GoalEventListener extends AbstractEventListener<GoalSetEvent> {
    public GoalEventListener(ObjectMapper objectMapper, List<EventHandler<GoalSetEvent>> goalAchievementHandlers) {
        super(GoalSetEvent.class, objectMapper, goalAchievementHandlers);
    }
}