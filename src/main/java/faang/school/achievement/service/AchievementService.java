package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementCache achievementCache;

    public AchievementDto getAchievement(String title) {
        return achievementCache.get(title);
    }
}
