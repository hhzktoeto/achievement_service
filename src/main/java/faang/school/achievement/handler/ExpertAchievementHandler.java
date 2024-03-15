package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExpertAchievementHandler extends CommentEventHandler {
    @Value("${spring.achievements.comment.expert}")
    private String expertAchievementName;

    public ExpertAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(Long userId) {
        log.info("Start process handle for achievement {}", expertAchievementName);
        executeHandle(userId, expertAchievementName);
    }
}