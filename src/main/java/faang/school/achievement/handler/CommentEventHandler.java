package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class CommentEventHandler implements EventHandler<CommentEventDto> {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    protected void executeHandle(Long userId, String achievementName) {
        Achievement achievement = achievementCache.get(achievementName);
        if (achievementService.hasAchievement(achievement, userId)) {
            return;
        }
        long achievementId = achievement.getId();
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress progress = achievementService.getProgress(userId, achievementId);
        progress.increment();
        achievementService.updateAchievementProgress(progress);
        log.info("Updated points of achievement {} to user {}", achievementName, userId);

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(achievement, userId);
            log.info("Achievement {} has been assigned to user {}", achievementName, userId);
        }
        log.info("Event has been processed to achievement {} for user {}", achievementName, userId);
    }
}