package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    AchievementService achievementService;
    private Achievement achievement;
    private Long userId;
    private Long achievementId;


    @BeforeEach
    public void setUp() {
        userId = 1L;
        achievementId = 2L;
        achievement = new Achievement();
        achievement.setId(achievementId);
    }

    @Test
    void testHasAchievement() {
        //Act
        achievementService.hasAchievement(achievement, userId);
        //Assert
        verify(userAchievementRepository, times(1))
                .existsByUserIdAndAchievementId(userId, achievement.getId());
    }

    @Test
    void testCreateProgressIfNecessary() {
        //Act
        achievementService.createProgressIfNecessary(userId, achievementId);
        //Assert
        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void testGetProgress() {
        //Arrange
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(new AchievementProgress()));
        //Act
        achievementService.getProgress(userId, achievementId);
        //Assert
        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void testGiveAchievement() {
        //Act
        achievementService.giveAchievement(achievement, userId);
        //Assert
        verify(userAchievementRepository, times(1)).save(any());
    }

    @Test
    void testUpdateAchievementProgress() {
        //Arrange
        AchievementProgress achievementProgress = new AchievementProgress();
        //Act
        achievementService.updateAchievementProgress(achievementProgress);
        //Assert
        verify(achievementProgressRepository, times(1)).save(achievementProgress);
    }
}