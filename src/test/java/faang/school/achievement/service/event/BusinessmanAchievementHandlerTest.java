package faang.school.achievement.service.event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.follower.FollowerEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
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
    @InjectMocks
    BloggerAchievementHandler bloggerAchievementHandler;

    private Achievement bloggerAchievement;
    private AchievementProgress bloggerAchievementProgress;
    private FollowerEvent followerEvent;

    @BeforeEach
    void setUp() {
        followerEvent = FollowerEvent.builder()
                .followeeId(1L)
                .followerId(2L)
                .build();
        bloggerAchievement = Achievement.builder()
                .id(10L)
                .title("BLOGGER")
                .build();
        bloggerAchievementProgress = AchievementProgress.builder()
                .achievement(bloggerAchievement)
                .currentPoints(0L)
                .userId(followerEvent.getFolloweeId())
                .build();
    }

    @Test
    void handle_UserHasntGotAchievement_AchievementProgressIncreased() throws NoSuchFieldException, IllegalAccessException {
        Field bloggerAchievementTitle = BloggerAchievementHandler.class.getDeclaredField("bloggerAchievementTitle");
        bloggerAchievementTitle.setAccessible(true);
        bloggerAchievementTitle.set(bloggerAchievementHandler, "BLOGGER");
        long userId = followerEvent.getFolloweeId();
        long achievementId = bloggerAchievement.getId();
        String achievementTitle = bloggerAchievement.getTitle();
        when(achievementCache.get(achievementTitle)).thenReturn(Optional.ofNullable(bloggerAchievement));
        when(achievementService.hasAchievement(userId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(bloggerAchievementProgress);

        bloggerAchievementHandler.handle(followerEvent);

        assertAll(
                () -> verify(achievementCache, times(1)).get(achievementTitle),
                () -> verify(achievementService, times(1)).hasAchievement(userId, achievementId),
                () -> verify(achievementService, times(1)).createProgressIfNecessary(userId, achievementId),
                () -> verify(achievementService, times(1)).getProgress(userId, achievementId),
                () -> verify(achievementService, times(1)).giveAchievement(userId, bloggerAchievement),
                () -> assertEquals(1L, bloggerAchievementProgress.getCurrentPoints())
        );
    }

    @Test
    void handle_UserAlreadyGotAchievement_HandlingIgnored() throws NoSuchFieldException, IllegalAccessException {
        Field bloggerAchievementTitle = BloggerAchievementHandler.class.getDeclaredField("bloggerAchievementTitle");
        bloggerAchievementTitle.setAccessible(true);
        bloggerAchievementTitle.set(bloggerAchievementHandler, "BLOGGER");
        long userId = followerEvent.getFolloweeId();
        long achievementId = bloggerAchievement.getId();
        String achievementTitle = bloggerAchievement.getTitle();
        when(achievementCache.get(bloggerAchievement.getTitle())).thenReturn(Optional.ofNullable(bloggerAchievement));
        when(achievementService.hasAchievement(userId, achievementId)).thenReturn(true);

        bloggerAchievementHandler.handle(followerEvent);

        assertAll(
                () -> verify(achievementCache, times(1)).get(achievementTitle),
                () -> verify(achievementService, times(1)).hasAchievement(userId, achievementId),
                () -> verify(achievementService, never()).createProgressIfNecessary(userId, achievementId),
                () -> verify(achievementService, never()).getProgress(userId, achievementId),
                () -> verify(achievementService, never()).giveAchievement(userId, bloggerAchievement)
        );
    }
}