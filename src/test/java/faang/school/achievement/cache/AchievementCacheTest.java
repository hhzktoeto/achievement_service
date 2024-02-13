package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementCacheTest {
    @Mock
    private AchievementRepository achievementRepository;
    @InjectMocks
    private AchievementCache achievementCache;

    @Test
    void shouldInitAndGet() {
        List<Achievement> achievements = List.of(
                Achievement.builder().title("title1").description("description1").build(),
                Achievement.builder().title("title2").description("description2").build());
        when(achievementRepository.findAll()).thenReturn(achievements);

        achievementCache.init();

        verify(achievementRepository, times(1)).findAll();
        assertEquals(achievementCache.get("title1"), achievements.get(0));
        assertEquals(achievementCache.get("title2"), achievements.get(1));
    }
}