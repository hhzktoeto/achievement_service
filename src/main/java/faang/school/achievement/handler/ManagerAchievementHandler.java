package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.TeamEventDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagerAchievementHandler implements EventHandler<TeamEventDto> {

    private final AchievementCache achievementCache;
    private AchievementService achievementService;

    @Async
    @Override
    public void handle(Long userId) {
            achievementCache.get()

    }
}
