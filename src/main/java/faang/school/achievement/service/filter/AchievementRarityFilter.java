package faang.school.achievement.service.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.List;

public class AchievementRarityFilter implements AchievementFilter{
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getRarity() != null;
    }

    @Override
    public void apply(List<Achievement> achievements, AchievementFilterDto filters) {
        achievements.removeIf(achievement -> !achievement.getRarity().equals(filters.getRarity()));
    }
}
