package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusinessmanAchievementHandler extends AbstractProjectCreateHandler {

    public BusinessmanAchievementHandler(AchievementService achievementService,
                                     AchievementCache achievementCache,
                                     @Value("${achievement.businessman.name}")
                                     String bloggerAchievementTitle) {
        super(achievementService, achievementCache, );
    }
}