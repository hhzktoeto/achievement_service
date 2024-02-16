package faang.school.achievement.listener;

import faang.school.achievement.dto.GoalSetEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoalEventListener extends AbstractEventListener<GoalSetEvent> {
    private final List<EventHandler<GoalSetEvent>> goalAchievementHandlers;

    public GoalEventListener(List<EventHandler<GoalSetEvent>> goalAchievementHandlers) {
        super(GoalSetEvent.class);
        this.goalAchievementHandlers = goalAchievementHandlers;
    }

    @Override
    protected void processEvent(GoalSetEvent event) {
        goalAchievementHandlers.forEach((x) -> x.handle(event.getUserId()));
    }
}
