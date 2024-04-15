package faang.school.achievement.service.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AchievementTitleFilter implements AchievementFilter{
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getTitle() != null;
    }

    @Override
    public void apply(List<Achievement> achievements, AchievementFilterDto filters) {
        achievements.removeIf(achievement -> !achievement.getTitle().contains(filters.getTitle()));
    }
}
