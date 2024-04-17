package faang.school.achievement.service;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapperImpl;
import faang.school.achievement.mapper.AchievementProgressMapperImpl;
import faang.school.achievement.mapper.UserAchievementMapperImpl;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Spy
    private AchievementMapperImpl achievementMapper;
    @Spy
    private UserAchievementMapperImpl userAchievementMapper;
    @Spy
    private AchievementProgressMapperImpl achievementProgressMapper;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private List<AchievementFilter> achievementFilters;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementService achievementService;
    private Achievement achievement1;
    private Achievement achievement2;
    private Achievement achievement3;
    private UserAchievement userAchievement1;
    private UserAchievement userAchievement2;
    private AchievementProgress progress1;
    private AchievementProgress progress2;

    @BeforeEach
    void init() {
        achievement1 = Achievement.builder()
                .id(1L)
                .build();
        achievement2 = Achievement.builder()
                .id(2L)
                .build();
        achievement3 = Achievement.builder()
                .id(3L)
                .build();
        userAchievement1 = UserAchievement.builder()
                .userId(1L)
                .build();
        userAchievement2 = UserAchievement.builder()
                .userId(2L)
                .build();
        progress1 = AchievementProgress.builder()
                .userId(1L)
                .build();
        progress2 = AchievementProgress.builder()
                .userId(2L)
                .build();
    }

    @Test
    public void testGetAllAchievement() {
        List<Achievement> achievements = Arrays.asList(achievement1, achievement2, achievement3);
        AchievementDto achievementDto1 = achievementMapper.toDto(achievement1);
        AchievementDto achievementDto2 = achievementMapper.toDto(achievement2);
        AchievementDto achievementDto3 = achievementMapper.toDto(achievement3);
        List<AchievementDto> expectedDto = Arrays.asList(achievementDto1, achievementDto2, achievementDto3);

        Mockito.when(achievementRepository.findAll()).thenReturn(achievements);
        Mockito.when(achievementFilters.isEmpty()).thenReturn(true);

        List<AchievementDto> actualDto = achievementService.getAllAchievements(new AchievementFilterDto());

        Assertions.assertEquals(expectedDto, actualDto);
        verify(achievementRepository, times(1)).findAll();
        verify(achievementFilters, times(1)).isEmpty();
        verify(achievementMapper, times(1)).toDtoAchievementsList(achievements);
    }

    @Test
    public void testGetAllAchievementByUser() {
        Long userId = 1L;
        List<UserAchievement> userAchievements = Arrays.asList(userAchievement1, userAchievement2);
        List<UserAchievementDto> expectedDtos = Arrays.asList(userAchievementMapper.toDto(userAchievement1),
                userAchievementMapper.toDto(userAchievement2));

        Mockito.when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);

        List<UserAchievementDto> actualDtos = achievementService.getAllUserAchievements(userId);

        Assertions.assertEquals(expectedDtos, actualDtos);
        verify(userAchievementRepository, times(1)).findByUserId(userId);
        verify(userAchievementMapper, times(1)).toDtoUserAchievementsList(userAchievements);
    }

    @Test
    public void testGetAchievementById() {
        Long achievementId = 1L;
        AchievementDto expectedDto = new AchievementDto();
        Mockito.when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement1));
        Mockito.when(achievementMapper.toDto(achievement1)).thenReturn(expectedDto);

        AchievementDto actualDto = achievementService.getAchievementById(achievementId);

        Assertions.assertEquals(expectedDto, actualDto);
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementMapper, times(1)).toDto(achievement1);
    }

    @Test
    public void testGetAchievementInProgressOfUser() {
        long userId = 1L;
        List<AchievementProgress> achievementProgress = Arrays.asList(progress1, progress2);
        List<AchievementProgressDto> expectedDtos = achievementProgressMapper.toListAchievementProgressDto(achievementProgress);

        Mockito.when(achievementProgressRepository.findByUserId(userId)).thenReturn(achievementProgress);

        List<AchievementProgressDto> actualDtos = achievementService.getProgressingUserAchievements(userId);

        Assertions.assertEquals(expectedDtos, actualDtos);
        verify(achievementProgressRepository, times(1)).findByUserId(userId);
    }
}


