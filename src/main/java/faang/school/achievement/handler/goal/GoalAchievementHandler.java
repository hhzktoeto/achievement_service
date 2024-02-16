package faang.school.achievement.handler.goal;

import faang.school.achievement.dto.GoalSetEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;

public abstract class GoalAchievementHandler implements EventHandler<GoalSetEvent> {
    protected abstract Achievement getAchievement();
    protected abstract AchievementService getAchievementService();

    @Override
    public void handle(Long userId) {
        Achievement achievement = getAchievement();
        AchievementService achievementService = getAchievementService();

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
}
