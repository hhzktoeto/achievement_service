package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
public abstract class AbstractProjectCreateHandler implements EventHandler<ProjectCreateEvent> {
    protected final AchievementService achievementService;
    protected final AchievementCache achievementCache;
    protected final String achievementTitle;

    @Override
    @Async("executorService")
    public void handle(ProjectCreateEvent event) {
        Achievement achievement = getAchievementFromCache();
        long userId = event.getUserId();
        long achievementId = achievement.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User {} already has achievement {}", userId, achievementTitle);
            return;
        }
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        achievementProgress.increment();
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
        }
    }

    private Achievement getAchievementFromCache() {
        return achievementCache.get(achievementTitle)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Achievement %s not found", achievementTitle)));
    }
}
