package faang.school.achievement.service;

import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;

    public void saveAchievement(UserAchievement achievement) {
        userAchievementRepository.save(achievement);
    }

//    public void createAchievement(long userId, long userAchievementId) {
//        userAchievementRepository.
//
//    }
}
