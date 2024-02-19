package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementProgressService {
    private final AchievementProgressRepository achievementProgressRepository;

    public AchievementProgress findByUserIdAndAchievementId(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("AchievementProgress not found"));
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public List<AchievementProgress> findByUserId(long userId) {
        return achievementProgressRepository.findByUserId(userId);
    }

    public boolean existsByUserIdAndAchievementId(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId).isPresent();
    }

    @Transactional
    public AchievementProgress incrementPoints(long userId, long achievementId) {
        if (!existsByUserIdAndAchievementId(userId, achievementId)) {
            createProgressIfNecessary(userId, achievementId);
        }
        AchievementProgress progress = findByUserIdAndAchievementId(userId, achievementId);
        progress.increment();
        return achievementProgressRepository.save(progress);
    }
}
