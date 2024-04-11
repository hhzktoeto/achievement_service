package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.comment.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpertAchievementHandler implements EventHandler<CommentEvent> {

    @Value("${achievements.comment.expert}")
    private String expertAchievementName;

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Async(value = "executorService")
    public void handle(CommentEvent event) {
        log.info("Received comment event: {}", event);
        Achievement achievement = achievementCache.get(expertAchievementName)
                .orElseThrow(() -> new EntityNotFoundException("Achievement does not exist"));
        if (!isUserHaveAchievement(event.getAuthorId(), achievement)) {
            achievementService.createProgressIfNecessary(event.getAuthorId(), achievement.getId());
            AchievementProgress achievementProgress = achievementService.getProgress(event.getAuthorId(), achievement.getId());
            achievementProgress.increment();
            if (isProgressPointsEnoughToAcquire(achievement.getPoints(), achievementProgress.getCurrentPoints())) {
                achievementService.giveAchievement(event.getAuthorId(), achievement);
                log.info(String.format("User %d reached the achievement \"%s\"", event.getAuthorId(), expertAchievementName));
            }
        }
    }

    private boolean isUserHaveAchievement(long userId, Achievement achievement) {
        return achievementService.hasAchievement(userId, achievement.getId());
    }

    private boolean isProgressPointsEnoughToAcquire(long requiredPoints, long achievementPoints) {
        return achievementPoints >= requiredPoints;
    }
}
