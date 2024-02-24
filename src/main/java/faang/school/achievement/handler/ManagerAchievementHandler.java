package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.TeamEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManagerAchievementHandler implements EventHandler<TeamEventDto> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Async
    @Transactional
    @Override
    public void handle(Long userId) {
        Achievement achievement = achievementCache.get("MANAGER");

        if (achievement != null && )



    }
}
