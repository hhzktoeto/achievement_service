package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.ProjectCreateEvent;
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
public class BusinessmanAchievementHandler implements EventHandler<ProjectCreateEvent> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Value("${achievement.businessman.name}")
    private String businessmanAchievementTitle;

    @Async
    @Override
    public void handle(ProjectCreateEvent event) {
        log.info("BloggerAchievementHandler handling follower event: {}", event);
        Achievement businessmanAchievement = getAchievementFromCache();
        long userId = event.getUserId();
        long projectId = event.getProjectId();
        long achievementId = businessmanAchievement.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User {} already has achievement {}", userId, businessmanAchievementTitle);
        }
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        achievementProgress.increment();
        if (achievementProgress.getCurrentPoints() >= businessmanAchievement.getPoints()) {
            achievementService.giveAchievement(userId, businessmanAchievement);
        }
    }

    private Achievement getAchievementFromCache() {
        return achievementCache.get(businessmanAchievementTitle)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Achievement %s not found", businessmanAchievementTitle)));
    }
}
