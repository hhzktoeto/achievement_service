package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @InjectMocks
    private AchievementService achievementService;

    @Captor

    private ArgumentCaptor<UserAchievement> captor;
    private long userId;
    private long achievementId;

    @BeforeEach
    void serUp(){
         userId = 1;
         achievementId =1;
    }

    @Test
    void testHasAchievement() {
        achievementService.hasAchievement(userId,achievementId);
        verify(userAchievementRepository,times(1))
                .existsByUserIdAndAchievementId(userId,achievementId);
    }

    @Test
    void testCreateProgressIdNecessary() {
        achievementService.createProgressIfNecessary(userId,achievementId);
        verify(achievementProgressRepository,times(1))
                .createProgressIfNecessary(userId,achievementId);
    }

    @Test
    void testGetProgress() {
        AchievementProgress achievementProgress = AchievementProgress.builder().build();
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId,achievementId))
                .thenReturn(Optional.ofNullable(achievementProgress));
        achievementService.getProgress(userId,achievementId);
        verify(achievementProgressRepository,times(1))
                .findByUserIdAndAchievementId(userId,achievementId);
    }

    @Test
    void testGetProgressThrowsException(){
        assertThrows(RuntimeException.class,()->achievementService.getProgress(userId,achievementId));
    }

    @Test
    void testGiveAchievement() {
        Achievement achievement = Achievement.builder().build();
        achievementService.giveAchievement(achievement,userId);
        verify(userAchievementRepository,times(1)).save(captor.capture());
    }

    @Test
    void testUserHasAchievement_UserHasAchievement_ReturnsTrue() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);

        boolean result = achievementService.userHasAchievement(userId, achievementId);

        assertTrue(result);
    }

    @Test
    void testUserHasAchievement_UserDoesNotHaveAchievement_ReturnsFalse() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(false);

        boolean result = achievementService.userHasAchievement(userId, achievementId);

        assertFalse(result);
    }

    @Test
    void testGetProgress_ProgressExists_ReturnsProgress() {
        AchievementProgress progress = new AchievementProgress();
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(progress));

        AchievementProgress result = achievementService.getProgress(userId, achievementId);

        assertNotNull(result);
    }

    @Test
    void testGetProgress_ProgressDoesNotExist_ThrowsEntityNotFoundException() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> achievementService.getProgress(userId, achievementId));
    }

    @Test
    void testCreateProgressIfNecessary_ProgressDoesNotExist_CreatesNewProgress() {
        achievementService.createProgressIfNecessary(userId, achievementId);

        verify(achievementProgressRepository, times(1)).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void testIncrementProgress_IncrementsProgress() {
        AchievementProgress progress = new AchievementProgress();
        achievementService.incrementProgress(progress);

        assertEquals(1, progress.getCurrentPoints());
    }

    @Test
    void testGiveAchievement_SavesUserAchievement() {
        Achievement achievement = new Achievement();
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();
        achievementService.giveAchievement(userId, achievement);

        verify(userAchievementRepository, times(1)).save(userAchievement);
    }
}