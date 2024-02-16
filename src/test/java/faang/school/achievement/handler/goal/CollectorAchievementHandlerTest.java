package faang.school.achievement.handler.goal;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.handler.goal.CollectorAchievementHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectorAchievementHandlerTest {

    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private CollectorAchievementHandler collectorAchievementHandler;

    @Test
    void testHandle_AchievementCompleted_IncrementsProgress() {
        long userId = 1L;
        Achievement achievement = new Achievement();
        achievement.setId(1L);
        AchievementProgress progress = new AchievementProgress();
        progress.setCurrentPoints(10);
        ReflectionTestUtils.setField(achievement, "points", 10);
        when(achievementCache.get("Collector")).thenReturn(achievement);
        when(achievementService.userHasAchievement(userId, achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, achievement.getId())).thenReturn(progress);

        collectorAchievementHandler.handle(userId);

        verify(achievementService, times(1)).createProgressIfNecessary(userId, achievement.getId());
        verify(achievementService, times(1)).incrementProgress(progress);
        verify(achievementService, times(1)).giveAchievement(userId, achievement);
    }

    @Test
    void testHandle_AchievementNotCompleted_DoesNotGiveAchievement() {
        long userId = 1L;
        Achievement achievement = new Achievement();
        achievement.setId(1L);
        AchievementProgress progress = new AchievementProgress();
        progress.setCurrentPoints(5);
        ReflectionTestUtils.setField(achievement, "points", 10);
        when(achievementCache.get("Collector")).thenReturn(achievement);
        when(achievementService.userHasAchievement(userId, achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, achievement.getId())).thenReturn(progress);

        collectorAchievementHandler.handle(userId);

        verify(achievementService, times(1)).createProgressIfNecessary(userId, achievement.getId());
        verify(achievementService, times(1)).incrementProgress(progress);
        verify(achievementService, never()).giveAchievement(userId, achievement);
    }
}
