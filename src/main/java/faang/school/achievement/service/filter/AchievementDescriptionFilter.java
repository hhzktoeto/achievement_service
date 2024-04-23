package faang.school.achievement.service.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.List;

public class AchievementDescriptionFilter implements AchievementFilter{
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getDescription() != null;
    }

    @Override
    public void apply(List<Achievement> achievements, AchievementFilterDto filters) {
        achievements.removeIf(achievement -> !achievement.getDescription().contains(filters.getDescription()));
    }
}
