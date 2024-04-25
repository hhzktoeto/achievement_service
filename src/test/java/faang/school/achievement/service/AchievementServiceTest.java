package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
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
    private AchievementService achievementService;

    @Test
    void hasAchievement_ValidArgs() {
        achievementService.hasAchievement(1L, 1L);

        verify(userAchievementRepository, times(1)).existsByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void createProgressIfNecessary_ValidArgs() {
        achievementService.createProgressIfNecessary(1L, 1L);

        verify(achievementProgressRepository, times(1)).createProgressIfNecessary(anyLong(), anyLong());
    }

    @Test
    void getProgress_ValidArgs() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(anyLong(), anyLong())).thenReturn(Optional.of(new AchievementProgress()));

        achievementService.getProgress(1L, 1L);

        verify(achievementProgressRepository, times(1)).findByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void getProgress_ProgressNotExist_ThrowsEntityNotFoundException() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> achievementService.getProgress(1L, 1L));
        verify(achievementProgressRepository, times(1)).findByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void giveAchievement_ValidArgs() {
        achievementService.giveAchievement(1L, new Achievement());

        verify(userAchievementRepository, times(1)).save(any(UserAchievement.class));
    }
}
