package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AlbumCreatedEventDto;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibrarianAchievementHandler extends AbstractEventHandler<AlbumCreatedEventDto>{
   //@Value("${spring.data.redis.channel.album}")
    private final String title = "LIBRARIAN";
    public LibrarianAchievementHandler(AchievementService achievementService,
                                       AchievementCache achievementCache,
                                       UserAchievementRepository userAchievementRepository,
                                       AchievementProgressRepository achievementProgressRepository) {
        super(achievementService, achievementCache, userAchievementRepository, achievementProgressRepository);
        //this.title = title;
    }
    @Retryable(maxAttempts = 3, value = {OptimisticLockException.class})
    @Override
    public void handle(Long userId) {
        handleAbstract(userId, title);
    }
}
