package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AchievementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@RequiredArgsConstructor
public class AchievementPublisher implements MessagePublisher<AchievementEvent> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementTopic;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(AchievementEvent achievementEvent) throws JsonProcessingException {
        redisTemplate.convertAndSend(achievementTopic.getTopic(), objectMapper.writeValueAsString(achievementEvent));
    }
}
