package faang.school.achievement.handler.follower;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractFollowerEventHandler<T> implements EventHandler<FollowerEvent> {

    private final AchievementService achievementService;

    public AbstractFollowerEventHandler(AchievementService achievementService,
                                        ) {
        this.achievementService = achievementService;
    }

    private static String AchievementTitle;

    protected void handleEvent(T event, Class<T> type, Consumer<T> consumer) throws IOException {
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
}

