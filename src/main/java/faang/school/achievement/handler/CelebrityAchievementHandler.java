package faang.school.achievement.handler;


import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CelebrityAchievementHandler extends AbstractEventHandler<FollowerEventDto> {

    private static final String ACHIEVEMENT_NAME = "CELEBRITY";
    private static final long REQUIRED_SUBSCRIPTIONS = 1_000_000;

    @Autowired
    public CelebrityAchievementHandler(AchievementService achievementService) {
        super(achievementService);
    }

    @Override
    protected String getAchievementName() {
        return ACHIEVEMENT_NAME;
    }

    @Override
    protected long getRequiredPoints() {
        return REQUIRED_SUBSCRIPTIONS;
    }
}
