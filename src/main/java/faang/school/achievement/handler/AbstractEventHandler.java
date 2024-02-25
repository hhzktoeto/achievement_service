package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public abstract class AbstractEventHandler<T>  implements EventHandler<T> {

    protected final AchievementCache achievementCache;
    protected final AchievementService achievementService;

    @Autowired
    protected AbstractEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        this.achievementCache = achievementCache;
        this.achievementService = achievementService;
    }

    public void handle(Long userId, String achievementName) {
        Achievement achievement = achievementCache.get(achievementName);
        log.info("Получили achievement");
        if (!achievementService.hasAchievement(userId, achievement.getId())) {
            AchievementProgress achievementProgress = achievementService
                    .createProgressIfNecessaryOrGet(userId, achievement.getId());
            AchievementProgress userAchievementProgress = achievementService.incrementProgress(achievementProgress);

            if (userAchievementProgress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }
}