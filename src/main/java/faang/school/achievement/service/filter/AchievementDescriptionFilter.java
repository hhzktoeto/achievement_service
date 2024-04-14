package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.List;

public class AchievementDescriptionFilter implements AchievementFilter{
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return false;
    }

    @Override
    public void apply(List<Achievement> achievements, AchievementFilterDto filters) {

    }
}
