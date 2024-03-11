package faang.school.achievement.handler.goal;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.GoalSetEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GoalAchievementHandler implements EventHandler<GoalSetEvent> {
    private AchievementService achievementService;
    private AchievementCache achievementCache;

    @Autowired
    public void init(AchievementService achievementService, AchievementCache achievementCache) {
        this.achievementService = achievementService;
        this.achievementCache = achievementCache;
    }

    @Override
    public void handle(Long userId) {
        Achievement achievement = achievementCache.get(getAchievementName());
        long achievementId = achievement.getId();

        if (achievementService.userHasAchievement(userId, achievementId)) {
            return;
        }

        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress progress = achievementService.getProgress(userId, achievementId);
        achievementService.incrementProgress(progress);

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
        }
    }

    protected abstract String getAchievementName();
}
