package faang.school.achievement.service.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AchievementRarityFilterTest {

    private Achievement achievementRare;
    private Achievement achievementCommon;
    private Achievement achievementEpic;

    @Test
    public void testAchievementRarityFilter() {
        achievementRare = Achievement.builder()
                .id(1L)
                .title("Rare Achievement")
                .description("Difficult one")
                .rarity(Rarity.RARE)
                .build();
        achievementCommon = Achievement.builder()
                .id(2L)
                .title("Common Achievement")
                .description("Easy one")
                .rarity(Rarity.COMMON)
                .build();
        achievementEpic = Achievement.builder()
                .id(3L)
                .title("Epic Achievement")
                .description("Very difficult")
                .rarity(Rarity.EPIC)
                .build();

        List<Achievement> achievements = new ArrayList<>();
        achievements.add(achievementRare);
        achievements.add(achievementCommon);
        achievements.add(achievementEpic);

        AchievementFilterDto filterWithRarity = new AchievementFilterDto();
        filterWithRarity.setRarity(Rarity.RARE);
        AchievementFilterDto filterWithoutRarity = new AchievementFilterDto();

        AchievementRarityFilter filter = new AchievementRarityFilter();

        assertTrue(filter.isApplicable(filterWithRarity));
        assertFalse(filter.isApplicable(filterWithoutRarity));


        List<Achievement> expectedWithRarity = new ArrayList<>(achievements);
        expectedWithRarity.removeIf(a -> !a.getRarity().equals(filterWithRarity.getRarity()));
        filter.apply(achievements, filterWithRarity);
        Assertions.assertEquals(expectedWithRarity, achievements);
    }
}
