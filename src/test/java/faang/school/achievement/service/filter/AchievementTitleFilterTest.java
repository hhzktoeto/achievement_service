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
public class AchievementTitleFilterTest {
    private Achievement achievement1;
    private Achievement achievement2;
    private Achievement achievement3;

    @Test
    public void testAchievementTitleFilter() {
        achievement1 = Achievement.builder()
                .id(1L)
                .title("a")
                .description("a")
                .build();
        achievement3 = Achievement.builder()
                .id(3L)
                .title("c")
                .description("c")
                .build();
        achievement2 = Achievement.builder()
                .id(2L)
                .title("b")
                .description("b")
                .build();

        List<Achievement> achievements = new ArrayList<>();
        achievements.add(achievement1);
        achievements.add(achievement2);
        achievements.add(achievement3);


        AchievementFilterDto filterWithTitle = new AchievementFilterDto();
        filterWithTitle.setTitle("a");
        AchievementFilterDto filterWithoutTitle = new AchievementFilterDto();

        AchievementTitleFilter filter = new AchievementTitleFilter();


        assertTrue(filter.isApplicable(filterWithTitle));
        assertFalse(filter.isApplicable(filterWithoutTitle));

        List<Achievement> expectedWithTitle = new ArrayList<>(achievements);
        expectedWithTitle.removeIf(a -> !a.getTitle().contains(filterWithTitle.getTitle()));
        filter.apply(achievements, filterWithTitle);
        Assertions.assertEquals(expectedWithTitle, achievements);
    }
}
