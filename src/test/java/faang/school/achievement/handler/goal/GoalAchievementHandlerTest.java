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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConcreteGoalAchievementHandler extends GoalAchievementHandler {
    @Override
    protected String getAchievementName() {
        return "Concrete Achievement";
    }
}
@ExtendWith(MockitoExtension.class)
public class GoalAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;

    @Mock
    private AchievementCache achievementCache;

    @InjectMocks
    private ConcreteGoalAchievementHandler goalAchievementHandler;

    long userId;
    Achievement achievement;
    AchievementProgress progress;

    @BeforeEach
    public void setup() {
        userId = 1;
        achievement = new Achievement();
        achievement.setId(1);
        achievement.setPoints(100);
        progress = new AchievementProgress();
        progress.setId(1);
        progress.setCurrentPoints(50);

        when(achievementCache.get("Concrete Achievement")).thenReturn(achievement);
        when(achievementService.userHasAchievement(userId, achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, achievement.getId())).thenReturn(progress);

    }

    @Test
    public void handle_UserDoesNotHaveAchievement_ProgressUpdated() {
        goalAchievementHandler.handle(userId);

        verify(achievementService).createProgressIfNecessary(userId, achievement.getId());
        verify(achievementService).incrementProgress(progress);
        verify(achievementService, never()).giveAchievement(userId, achievement);
    }

    @Test
    public void handle_UserAchievesGoal_AchievementGranted() {
        long userId = 1;
        Achievement achievement = new Achievement();
        achievement.setId(1);
        achievement.setPoints(100);
        AchievementProgress progress = new AchievementProgress();
        progress.setId(1);
        progress.setCurrentPoints(100);

        when(achievementCache.get("Concrete Achievement")).thenReturn(achievement);
        when(achievementService.userHasAchievement(userId, achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(userId, achievement.getId())).thenReturn(progress);

        goalAchievementHandler.handle(userId);

        verify(achievementService).createProgressIfNecessary(userId, achievement.getId());
        verify(achievementService).incrementProgress(progress);
        verify(achievementService).giveAchievement(userId, achievement);
    }
}
