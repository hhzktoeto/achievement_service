package faang.school.achievement.handler.taskCompleted;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MrProductivityAchievementHandler extends TaskCompletedHandler {
    private static final String MR_PRODUCTIVITY = "MR PRODUCTIVITY";

    @Override
    public void handle(Long userId) {
        processAchievement(userId, MR_PRODUCTIVITY);
    }
}
