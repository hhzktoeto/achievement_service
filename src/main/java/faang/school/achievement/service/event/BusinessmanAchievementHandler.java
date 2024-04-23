package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BusinessmanAchievementHandler extends AbstractAchievementHandler<ProjectCreateEvent> {

    @Value("${achievement.businessman.name}")
    private String achievementName;

    public BusinessmanAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    protected String getAchievementTitle() {
        return achievementName;
    }

    @Override
    protected long getUserIdFromEvent(ProjectCreateEvent event) {
        return event.getUserId();
    }
}