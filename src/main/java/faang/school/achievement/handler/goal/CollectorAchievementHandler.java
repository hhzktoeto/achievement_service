package faang.school.achievement.handler.goal;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectorAchievementHandler extends GoalAchievementHandler {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Override
    protected Achievement getAchievement() {
        return achievementCache.get("Collector");
    }

    @Override
    protected AchievementService getAchievementService() {
        return achievementService;
    }

    @Override
    public void handle(Long userId) {
        super.handle(userId);
    }
}
