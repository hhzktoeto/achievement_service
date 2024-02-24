package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.TeamEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerAchievementHandler implements EventHandler<TeamEventDto> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Override
    public void handle(Long userId) {
        Achievement achievement = achievementCache.get("MANAGER");
        log.info("Получили achievement");
        if (achievement != null && !achievementService.hasAchievement(userId, achievement.getId())) {
            AchievementProgress achievementProgress = achievementService
                    .createProgressIfNecessaryOrGet(userId, achievement.getId());
            AchievementProgress userAchievementProgress = achievementService.incrementProgress(achievementProgress);

            if (userAchievementProgress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }
}
