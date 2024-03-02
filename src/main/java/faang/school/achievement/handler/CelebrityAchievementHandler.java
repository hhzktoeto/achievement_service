package faang.school.achievement.handler;


import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CelebrityAchievementHandler extends AbstractEventHandler<FollowerEvent> {

    private static final String achievementName = "CELEBRITY";
    private static final long REQUIRED_SUBSCRIPTIONS = 1_000_000;

    @Autowired
    public CelebrityAchievementHandler(AchievementService achievementService) {
        super(achievementService);
    }

    @Override
    protected String getAchievementName() {
        return achievementName;
    }

    @Override
    protected long getRequiredPoints() {
        return REQUIRED_SUBSCRIPTIONS;
    }
}
