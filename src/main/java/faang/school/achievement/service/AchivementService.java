package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchivementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    public Achievement getAchievmentByTitle(String title) {
        return null;
    }

    public boolean hasAchievment(long userId, long achievmentId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievmentId);
    }

    public void createProgressIfNecessary(long userId, long achievmentId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievmentId);
    }

    public Optional<AchievementProgress> getProgress(long userId, long achievmentId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievmentId);
    }

}
