package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.UserAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenseyAchievementHandler implements EventHandler {
    private final String achievementTitle = "Achievement-Sensei";
    private final AchievementCache achievementCache;
    private final UserAchievementService userAchievementService;
    private final AchievementProgressService achievementProgressService;

    @Override
    @Async
    public void handle(Long userId) {
        Achievement achievement = achievementCache.get(achievementTitle);

        if (!userAchievementService.existsByUserIdAndAchievementId(userId, achievement.getId())) {
            AchievementProgress progress = achievementProgressService.incrementPoints(userId, achievement.getId());
            if (progress.getCurrentPoints() >= achievement.getPoints()) {
                userAchievementService.addAchievementToUser(userId, achievement.getId());
            }
        }
    }
}
