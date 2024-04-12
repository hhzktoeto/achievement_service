package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.follower.FollowerEvent;
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
public class BloggerAchievementHandler implements EventHandler<FollowerEvent> {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Value("${achievement.blogger.name}")
    private String bloggerAchievementTitle;

    @Override
    @Async("executorService")
    public void handle(FollowerEvent event) {
        log.info("BloggerAchievementHandler handling follower event: {}", event);
        Achievement bloggerAchievement = getAchievementFromCache();
        long userId = event.getFolloweeId();
        long achievementId = bloggerAchievement.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User {} already has achievement {}", userId, bloggerAchievementTitle);
            return;
        }
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        achievementProgress.increment();
        if (achievementProgress.getCurrentPoints() >= bloggerAchievement.getPoints()) {
            achievementService.giveAchievement(userId, bloggerAchievement);
        }
    }

    private Achievement getAchievementFromCache() {
        return achievementCache.get(bloggerAchievementTitle)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Achievement %s not found", bloggerAchievementTitle)));
    }
}
