package faang.school.achievement.service.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class AchievementDescriptionFilterTest {

    private Achievement achievement1;
    private Achievement achievement2;
    private Achievement achievement3;

    @Test
    public void testAchievementDescriptionFilter() {
        achievement1 = Achievement.builder()
                .id(1L)
                .title("Master Coder")
                .description("Reach level 10 in coding")
                .build();
        achievement2 = Achievement.builder()
                .id(2L)
                .title("Super Achiever")
                .description("Complete 100 challenges")
                .build();
        achievement3 = Achievement.builder()
                .id(3L)
                .title("Another Achievement")
                .description("More description...")
                .build();

        List<Achievement> achievements = new ArrayList<>();
        achievements.add(achievement1);
        achievements.add(achievement2);
        achievements.add(achievement3);

        AchievementFilterDto filterWithDescription = new AchievementFilterDto();
        filterWithDescription.setDescription("level");
        AchievementFilterDto filterWithoutDescription = new AchievementFilterDto();

        AchievementDescriptionFilter filter = new AchievementDescriptionFilter();

        assertTrue(filter.isApplicable(filterWithDescription));
        assertFalse(filter.isApplicable(filterWithoutDescription));

        List<Achievement> expectedWithDescription = new ArrayList<>(achievements);
        expectedWithDescription.removeIf(a -> !a.getDescription().contains(filterWithDescription.getDescription()));
        filter.apply(achievements, filterWithDescription);
        Assertions.assertEquals(expectedWithDescription, achievements);
    }
}
