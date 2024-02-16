package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final Map<String, Achievement> achievementCache = new HashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void init() {
        achievementRepository.findAll()
                .forEach(achievement -> achievementCache.put(achievement.getTitle(), achievement));
    }

    public Achievement get(String title) {
        return achievementCache.get(title);
    }
}