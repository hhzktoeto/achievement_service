package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractAchievementHandler<T> implements EventHandler<T> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    protected abstract String getAchievementTitle();

    @Async
    @Override
    public void handle(T event) {
        log.info("Handling event: {}", event);
        String achievementTitle = getAchievementTitle();
        Achievement achievement = getAchievementFromCache(achievementTitle);
        long userId = getUserIdFromEvent(event);
        long achievementId = achievement.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User {} already has achievement {}", userId, achievementTitle);
            log.info("Skipping further processing as the user already has this achievement.");
            return;
        }

        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        achievementProgress.increment();
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
        }
    }

    private Achievement getAchievementFromCache(String achievementTitle) {
        return achievementCache.get(achievementTitle)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Achievement %s not found", achievementTitle)));
    }

    protected abstract long getUserIdFromEvent(T event);
}
