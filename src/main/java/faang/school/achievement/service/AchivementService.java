package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchivementService {

    private final AchievementRepository achievementRepository;

    public Achievement getAchievmentByTitle(String title) {
        return null;
    }

    public boolean hasAchievment(long achievmentId, long userId) {
        return false;
    }

    public void createProgressIfNecessary(long achievmentId, long userId) {
        return;
    }

    public void getProgress(long achievmentId, long userId) {
        return;
    }

}
