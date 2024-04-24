package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.event.businessman.BusinessmanAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BloggerAchievementHandlerTest {

    @Mock
    AchievementCache achievementCache;
    @Mock
    AchievementService achievementService;
    BusinessmanAchievementHandler businessmanAchievementHandler;

    private Achievement businessmanAchievement;
    private AchievementProgress businessmanAchievementProgress;
    private ProjectCreateEvent projectCreateEvent;
    private String businessmanAchievementTitle;

    @BeforeEach
    void setUp() {
        projectCreateEvent = ProjectCreateEvent.builder()
                .userId(1L)
                .projectId(2L)
                .build();
        businessmanAchievement = Achievement.builder()
                .id(10L)
                .title("BUSINESSMAN")
                .build();
        businessmanAchievementProgress = AchievementProgress.builder()
                .achievement(businessmanAchievement)
                .currentPoints(0L)
                .userId(projectCreateEvent.getUserId())
                .build();
        businessmanAchievementTitle = "BUSINESSMAN";
        businessmanAchievementHandler = new BusinessmanAchievementHandler(achievementService, achievementCache, businessmanAchievementTitle);
    }

    @Test
    void handle_UserHasntGotAchievement_AchievementProgressIncreased() throws NoSuchFieldException, IllegalAccessException {
        long userId = projectCreateEvent.getUserId();
        long achievementId = businessmanAchievement.getId();
        String achievementTitle = businessmanAchievement.getTitle();
        when(achievementCache.get(achievementTitle)).thenReturn(Optional.ofNullable(businessmanAchievement));
        when(achievementService.hasAchievement(userId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(businessmanAchievementProgress);

        businessmanAchievementHandler.handle(projectCreateEvent);

        assertAll(
                () -> verify(achievementCache, times(1)).get(achievementTitle),
                () -> verify(achievementService, times(1)).hasAchievement(userId, achievementId),
                () -> verify(achievementService, times(1)).createProgressIfNecessary(userId, achievementId),
                () -> verify(achievementService, times(1)).getProgress(userId, achievementId),
                () -> verify(achievementService, times(1)).giveAchievement(userId, businessmanAchievement),
                () -> assertEquals(1L, businessmanAchievementProgress.getCurrentPoints())
        );
    }

    @Test
    void handle_UserAlreadyGotAchievement_HandlingIgnored() throws NoSuchFieldException, IllegalAccessException {
        long userId = projectCreateEvent.getUserId();
        long achievementId = businessmanAchievement.getId();
        String achievementTitle = businessmanAchievement.getTitle();
        when(achievementCache.get(businessmanAchievement.getTitle())).thenReturn(Optional.ofNullable(businessmanAchievement));
        when(achievementService.hasAchievement(userId, achievementId)).thenReturn(true);

        businessmanAchievementHandler.handle(projectCreateEvent);

        assertAll(
                () -> verify(achievementCache, times(1)).get(achievementTitle),
                () -> verify(achievementService, times(1)).hasAchievement(userId, achievementId),
                () -> verify(achievementService, never()).createProgressIfNecessary(userId, achievementId),
                () -> verify(achievementService, never()).getProgress(userId, achievementId),
                () -> verify(achievementService, never()).giveAchievement(userId, businessmanAchievement)
        );
    }
}
