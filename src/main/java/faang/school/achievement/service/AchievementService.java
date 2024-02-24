package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    public boolean hasAchievement(Achievement achievement, Long userId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievement.getId());
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional
    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement Progress not found for user {}" + userId));
    }

    @Transactional
    public void giveAchievement(Achievement achievement, long userId) {
        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setAchievement(achievement);
        userAchievement.setUserId(userId);
        userAchievementRepository.save(userAchievement);
    }

    @Transactional
    public void updateAchievementProgress(AchievementProgress achievementProgress) {
        achievementProgressRepository.save(achievementProgress);
    }
}