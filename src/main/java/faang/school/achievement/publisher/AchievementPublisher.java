package faang.school.achievement.publisher;

import faang.school.achievement.event.AchievementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@RequiredArgsConstructor
public class AchievementPublisher implements MessagePublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementTopic;

    @Override
    public void publish(AchievementEvent achievementEvent) {
        redisTemplate.convertAndSend(achievementTopic.getTopic(), achievementEvent);
    }
}
