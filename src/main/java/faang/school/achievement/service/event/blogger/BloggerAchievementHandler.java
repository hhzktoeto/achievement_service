package faang.school.achievement.service.event.blogger;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.event.AbstractFollowerEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BloggerAchievementHandler extends AbstractFollowerEventHandler {

    public BloggerAchievementHandler(AchievementService achievementService,
                                     AchievementCache achievementCache,
                                     @Value("${achievement.blogger.name}")
                                     String bloggerAchievementTitle) {
        super(achievementService, achievementCache, bloggerAchievementTitle);
    }
}
