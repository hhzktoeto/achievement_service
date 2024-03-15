package faang.school.achievement.handler.goal;

import org.springframework.stereotype.Component;

@Component
public class CollectorAchievementHandler extends GoalAchievementHandler {
    @Override
    protected String getAchievementName() {
        return "Collector";
    }
}