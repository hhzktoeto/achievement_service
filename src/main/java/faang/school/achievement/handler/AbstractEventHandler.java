package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventHandler<T> implements EventHandler<T> {

    private final AchievementService achievementService;

    @Override
    public void handle(Long userId) {
        Achievement achievement = achievementService.getAchievement(getAchievementName());
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(userId, achievementId)) {
            Optional<AchievementProgress> achievementProgress = achievementService.getProgress(userId, achievementId);
            if (achievementProgress.isEmpty()) {
                achievementService.createProgressIfNecessary(userId, achievementId);
                achievementProgress = achievementService.getProgress(userId, achievementId);
            }

            AchievementProgress progress = achievementProgress.get();

            if (progress.getCurrentPoints() < getRequiredPoints()) {
                progress.increment();
                achievementService.saveAchievementProgress(progress);
            } else {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }

    protected abstract String getAchievementName();

    protected abstract long getRequiredPoints();

}
