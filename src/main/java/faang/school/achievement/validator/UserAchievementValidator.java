package faang.school.achievement.validator;

import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAchievementValidator {

    private final UserAchievementRepository userAchievementRepository;

    public void validate(long userId, long userAchievementId) {
        userAchievementRepository.existsByUserIdAndAchievementId(userId, userAchievementId);
    }
}
