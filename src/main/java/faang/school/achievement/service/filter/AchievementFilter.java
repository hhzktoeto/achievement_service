package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.List;

public interface AchievementFilter {
    boolean isApplicable(AchievementFilterDto filters);

    void apply(List<Achievement> achievements, AchievementFilterDto filters);
}
