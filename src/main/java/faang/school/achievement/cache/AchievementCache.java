package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final AchievementRepository achievementRepository;

    private static Map<String, Achievement> achievements = new HashMap<>();

    @PostConstruct
    public void init() {
        achievements = achievementRepository.findAll().stream()
                .collect(Collectors.toMap(Achievement::getTitle, Function.identity()));
    }

    public Optional<Achievement> get(String achievementTitle) {
        return Optional.ofNullable(achievements.get(achievementTitle));
    }
}