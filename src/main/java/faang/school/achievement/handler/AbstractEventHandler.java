package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.validator.UserAchievementValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AbstractEventHandler<T> implements EventHandler<T> {

    private final UserAchievementValidator validator;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementRepository achievementRepository;
    private String title;

    @Override
    public void handle(Long userId) {

    }
}
