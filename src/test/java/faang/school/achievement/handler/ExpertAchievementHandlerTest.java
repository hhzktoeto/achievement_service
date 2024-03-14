package faang.school.achievement.handler;

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

import static org.junit.jupiter.api.Assertions.*;
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
    ExpertAchievementHandler expertAchievementHandler;
    Long userId;
    Long achievementId;
    Achievement achievement;
    AchievementProgress progress;
    String achievementName;

    @BeforeEach
    void setUp() {
        userId = 1L;
        achievementName = "SomeAchieve";
        achievementId = 2L;
        achievement = Achievement.builder().id(achievementId).build();
        progress = new AchievementProgress();

        when(achievementCache.get(achievementName)).thenReturn(achievement);
    }

    @Test
    void testHandle_whenUserDontHaveAchievement_thenUpdatePointsAndDontGiveAchievement() {
        //Arrange
        progress.setCurrentPoints(10);
        achievement.setPoints(1000);
        when(achievementService.hasAchievement(achievementId, userId)).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);

        //Act
        expertAchievementHandler.executeHandle(userId, achievementName);

        //Assert
        assertAll(() -> verify(achievementCache, times(1)).get(achievementName),
                () -> verify(achievementService, times(1)).createProgressIfNecessary(userId, achievementId),
                () -> verify(achievementService, times(1)).getProgress(userId, achievementId),
                () -> verify(achievementService, times(1)).updateProgress(progress),
                () -> verify(achievementService, times(0)).giveAchievement(achievement, userId)
        );
    }

    @Test
    void testHandle_whenUserDontHaveAchievement_thenUpdatePointsAndGiveAchievement() {
        //Arrange
        progress.setCurrentPoints(999);
        achievement.setPoints(1000);
        when(achievementService.hasAchievement(achievementId, userId)).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);

        //Act
        expertAchievementHandler.executeHandle(userId, achievementName);

        //Assert
        assertAll(() -> verify(achievementCache, times(1)).get(achievementName),
                () -> verify(achievementService, times(1)).createProgressIfNecessary(userId, achievementId),
                () -> verify(achievementService, times(1)).getProgress(userId, achievementId),
                () -> verify(achievementService, times(1)).updateProgress(progress),
                () -> verify(achievementService, times(1)).giveAchievement(achievement, userId)
        );
    }

    @Test
    void testHandle_whenUserHaveAchievement_thenDoNothing() {
        //Arrange
        when(achievementService.hasAchievement(achievementId, userId)).thenReturn(true);

        //Act
        expertAchievementHandler.executeHandle(userId, achievementName);

        //Assert
        assertAll(() -> verify(achievementCache, times(1)).get(achievementName),
                () -> verify(achievementService, times(0)).createProgressIfNecessary(userId, achievementId),
                () -> verify(achievementService, times(0)).getProgress(userId, achievementId),
                () -> verify(achievementService, times(0)).updateProgress(progress),
                () -> verify(achievementService, times(0)).giveAchievement(achievement, userId)
        );
    }
}