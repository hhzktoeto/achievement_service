package faang.school.achievement.service;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    public List<AchievementDto> getAllAchievements(AchievementFilterDto filters) {
        List<Achievement> achievements = achievementRepository.findAll();

        if (!achievementFilters.isEmpty()) {
            achievementFilters.stream()
                    .filter(filter -> filter.isApplicable(filters))
                    .forEach(filter -> filter.apply(achievements, filters));
        }
        return achievementMapper.toDtoAchievementsList(achievements);
    }

    public List<UserAchievementDto> getAllUserAchievements(Long userId) {
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);
        return userAchievementMapper.toDtoUserAchievementsList(userAchievements);
    }

    public AchievementDto getAchievementById(Long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId).orElseThrow(() -> new EntityNotFoundException("Achievement for this" + achievementId + "not found"));
        return achievementMapper.toDto(achievement);
    }

    public List<AchievementProgressDto> getProgressingUserAchievements(Long userId) {
        List<AchievementProgress> achievementProgress = achievementProgressRepository.findByUserId(userId);
        return achievementProgressMapper.toListAchievementProgressDto(achievementProgress);
    }
}
