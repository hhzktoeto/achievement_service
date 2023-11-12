package faang.school.achievement.service.handler.SenseyAchievementHandler;

import faang.school.achievement.dto.follow.Mentorship.MentorshipEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.cache.AchievementInMemCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SenseyAchievementHandler implements EventHandler<MentorshipEventDto> {
    private final AchievementService service;
    private final AchievementInMemCache achievementCache;
    @Value("${achievements.sensey_achievement}")
    private String achievementName;

    @Override
    @Async("eventHandlerExecutor")
    public void handle(MentorshipEventDto mentorshipEventDto) {
        Optional<Achievement> achievement = achievementCache.getAchievement(achievementName);
        service.updateAchievementProgress(mentorshipEventDto.getUserId(), achievement.get());
        log.info("{} successfully handled", mentorshipEventDto);
    }
}
