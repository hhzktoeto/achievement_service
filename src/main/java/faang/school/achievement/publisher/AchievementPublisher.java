package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AchievementEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementPublisher implements MessagePublisher<AchievementEvent> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementTopic;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(AchievementEvent achievementEvent) {
        try {
            redisTemplate.convertAndSend(achievementTopic.getTopic(), objectMapper.writeValueAsString(achievementEvent));
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException was thrown");
            throw new SerializationException("Failed to serialized message", e);
        }
    }
}
