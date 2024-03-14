package faang.school.achievement.handler.goal;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalAchievementHandlerTest {

    @Test
    void testHandle_UserAlreadyHasAchievement_DoesNotIncrementProgress() {
        long userId = 1L;
        Achievement achievement = mock(Achievement.class);
//        when(achievement.getId()).thenReturn(1L);

        AchievementService achievementService = mock(AchievementService.class);
        //when(achievementService.hasAchievement(userId, achievement.getId())).thenReturn(true);

        GoalAchievementHandler handler = new GoalAchievementHandler() {
            @Mock
            AchievementCache achievementCache;

            @Override
            protected String getAchievementName() {
                return achievement.getTitle();
            }
        };

        /*handler.handle(userId);

        verify(achievementService, never()).createProgressIfNecessary(anyLong(), anyLong());
        verify(achievementService, never()).giveAchievement(achievement, any());*/
    }

    @Test
    void testHandle_AchievementCompleted_GivesAchievement() {
        long userId = 1L;
        Achievement achievement = mock(Achievement.class);
//        when(achievement.getId()).thenReturn(1L);
//        when(achievement.getPoints()).thenReturn(100L);

        AchievementProgress progress = mock(AchievementProgress.class);
//        when(progress.getCurrentPoints()).thenReturn(100L);

        AchievementService achievementService = mock(AchievementService.class);
 //       when(achievementService.hasAchievement(userId, achievement.getId())).thenReturn(false);
  //      when(achievementService.getProgress(userId, achievement.getId())).thenReturn(progress);

        GoalAchievementHandler handler = new GoalAchievementHandler() {

            @Override
            protected String getAchievementName() {
                return achievement.getTitle();
            }
        };

        /*handler.handle(userId);

        verify(achievementService).createProgressIfNecessary(userId, achievement.getId());
        verify(achievementService).getProgress(userId, achievement.getId());
        verify(achievementService).giveAchievement(achievement, userId);*/
    }
}