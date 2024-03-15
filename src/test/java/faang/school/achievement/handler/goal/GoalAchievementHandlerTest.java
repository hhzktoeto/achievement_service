package faang.school.achievement.handler.goal;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

class TestGoalAchievementHandler extends GoalAchievementHandler {
    @Override
    protected String getAchievementName() {
        return "Test Achievement";
    }
}
@ExtendWith(MockitoExtension.class)
public class GoalAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;

    @Mock
    private AchievementCache achievementCache;

    @InjectMocks
    private TestGoalAchievementHandler handler;

    @Test
    public void handle_withAchievementNotOwned_progressIncrementedAndAchievementGranted() {
        long userId = 1L;
        Achievement achievement = new Achievement();
        achievement.setId(1L);
        achievement.setPoints(5L);
        AchievementProgress progress = new AchievementProgress();
        progress.setCurrentPoints(4L);

        when(achievementCache.get("Test Achievement")).thenReturn(achievement);
        when(achievementService.hasAchievement(userId, achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, achievement.getId())).thenReturn(progress);

        handler.handle(userId);

        verify(achievementService).createProgressIfNecessary(userId, achievement.getId());
        verify(achievementService).getProgress(userId, achievement.getId());
        verify(achievementService).giveAchievement(achievement, userId);
    }

    @Test
    public void handle_withAchievementOwned_noActionTaken() {
        long userId = 1L;
        Achievement achievement = new Achievement();
        achievement.setId(1L);

        when(achievementCache.get("Test Achievement")).thenReturn(achievement);
        when(achievementService.hasAchievement(userId, achievement.getId())).thenReturn(true);

        handler.handle(userId);

        verify(achievementService, never()).createProgressIfNecessary(anyLong(), anyLong());
        verify(achievementService, never()).getProgress(anyLong(), anyLong());
        verify(achievementService, never()).giveAchievement(any(Achievement.class), anyLong());
    }
}