package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.comment.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpertAchievementHandlerTest {

    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private ExpertAchievementHandler expertAchievementHandler;

    private AchievementProgress achievementProgress;

    private int progressPoints;

    private int expectedPoints;

    @BeforeEach
    void setUp() throws Exception {
        Field expertAchievementName = ExpertAchievementHandler.class.getDeclaredField("expertAchievementName");
        expertAchievementName.setAccessible(true);
        expertAchievementName.set(expertAchievementHandler, "EXPERT");

        progressPoints = 1;
        expectedPoints = progressPoints + 1;

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(new Achievement())
                .userId(1L)
                .currentPoints(progressPoints)
                .build();
    }

    @Test
    void handle_ValidArgs() {
        when(achievementCache.get(anyString())).thenReturn(Optional.of(Achievement.builder().points(1).build()));
        when(achievementService.hasAchievement(anyLong(), anyLong())).thenReturn(false);
        when(achievementService.getProgress(anyLong(), anyLong())).thenReturn(achievementProgress);

        expertAchievementHandler.handle(new CommentEvent());

        assertEquals(expectedPoints, achievementProgress.getCurrentPoints());
        verify(achievementCache, times(1)).get(anyString());
        verify(achievementService, times(1)).hasAchievement(anyLong(), anyLong());
        verify(achievementService, times(1)).createProgressIfNecessary(anyLong(), anyLong());
        verify(achievementService, times(1)).getProgress(anyLong(), anyLong());
        verify(achievementService, times(1)).giveAchievement(anyLong(), any(Achievement.class));
    }

    @Test
    void handle_AchievementNotExist_ThrowsEntityNotFoundException() {
        when(achievementCache.get(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> expertAchievementHandler.handle(new CommentEvent()));
    }
}
