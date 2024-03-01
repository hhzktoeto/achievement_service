package faang.school.achievement.handler.follower.achievments;

import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.handler.follower.AbstractFollowerEventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CelebrityHandler extends AbstractFollowerEventHandler<FollowerEvent> {

    private static final String CELEBRITY_ACHIEVEMENT = "Celebrity";
    private final AchievementService achievementService;

    @Async
    public void handle(FollowerEvent event) {
        Achievement achievement = achievementService.getAchievement(CELEBRITY_ACHIEVEMENT);
        long userId = event.getFolloweeId();
        long achievementId = achievement.getId();
        if (!achievementService.hasAchievement(userId, achievementId)) {
            Optional<AchievementProgress> achievementProgress = achievementService.getProgress(userId, achievementId);
            if (achievementProgress.isEmpty()) {
                achievementService.createProgressIfNecessary(userId, achievementId);
                achievementProgress = achievementService.getProgress(userId, achievementId);
            }

            AchievementProgress progress = achievementProgress.get();

            if (progress.getCurrentPoints() < achievement.getPoints()) {
                progress.increment();
                achievementService.saveAchievementProgress(progress);
            } else {
                achievementService.giveAchievement(userId, achievement);
            }
        }

    }

    @Override
    public void handle(Long userId) {

    }
}
