package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenseyAchievementHandler implements EventHandler {
    private final String achievementTitle = "Achievement-Sensei";
    private final AchievementCache achievementCache;

    @Override
    @Async
    public void handle(Long userId) {
        Achievement achievement = achievementCache.get(achievementTitle);

    }
}
