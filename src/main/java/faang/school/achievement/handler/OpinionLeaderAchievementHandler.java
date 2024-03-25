package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.event.PostEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends AbstractEventHandler implements EventHandler<PostEvent>{
    String achievementTitle = "WRITER";

    public OpinionLeaderAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(Long userId) {
        abstractHandle(userId, achievementTitle);
    }
}
