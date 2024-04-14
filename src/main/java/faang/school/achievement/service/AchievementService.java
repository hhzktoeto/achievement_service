package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementDto achievementDto;
    private final AchievementRepository achievementRepository;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementMapper achievementMapper;
    private final UserAchievementRepository userAchievementRepository;

    public List<AchievementDto> findAllAchievement(AchievementFilterDto filters) {
        List<Achievement> achievements = (List<Achievement>) achievementRepository.findAll();

        if (!achievementFilters.isEmpty()) {
            achievementFilters.stream()
                    .filter(filter -> filter.isApplicable(filters))
                    .forEach(filter -> filter.apply(achievements, filters));
        }
        return achievementMapper.toDtoAchievements(achievements);
    }
    public List<UserAchievementDto> findAllAchievementByUser(Long userId){
        List<UserAchievement> userAchievements = (List<UserAchievement>) userAchievementRepository.findAllById(Collections.singletonList(userId));
        return achievementMapper.toDtoUserAchievements(userAchievements);
    }

    public AchievementDto findAchievementById(Long achievementId){
        return achievementMapper.toDtoUserAchievements(achievementRepository.findById(achievementId));
    }

    public List<AchievementDto> findAcivByUserInProgress(Long userId){
        findAllAchievementByUser(userId).

    }

}
