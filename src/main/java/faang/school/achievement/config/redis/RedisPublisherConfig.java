package faang.school.achievement.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisPublisherConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private Integer port;

    @Value("${spring.data.redis.channel.achievement}")
    private String achievementTopic;

    private final ObjectMapper objectMapper;

    @Bean
    ChannelTopic achievementTopic() {
        return new ChannelTopic(achievementTopic);
    }

    @Bean
    public JedisConnectionFactory publisherRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> publisherTemplate = new RedisTemplate<>();
        publisherTemplate.setConnectionFactory(publisherRedisConnectionFactory());
        publisherTemplate.setKeySerializer(new StringRedisSerializer());
        publisherTemplate.setValueSerializer(new StringRedisSerializer());
        return publisherTemplate;
    }
}
