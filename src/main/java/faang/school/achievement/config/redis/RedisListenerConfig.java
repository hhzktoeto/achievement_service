package faang.school.achievement.config.redis;

import faang.school.achievement.listener.ProjectCreateEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RedisListenerConfig {

    private final ProjectCreateEventListener projectCreateEventListener;

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channel.project-create-channel}")
    private String projectCreateChannel;

    @Bean
    JedisConnectionFactory redisListenerConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    RedisTemplate<String, Object> redisListenerTemplate() {
        final RedisTemplate<String, Object> listenerTemplate = new RedisTemplate<String, Object>();
        listenerTemplate.setConnectionFactory(redisListenerConnectionFactory());
        listenerTemplate.setKeySerializer(new StringRedisSerializer());
        listenerTemplate.setValueSerializer(new StringRedisSerializer());
        return listenerTemplate;
    }

    @Bean
    MessageListenerAdapter projectCreateListenerAdapter() {
        return new MessageListenerAdapter(projectCreateEventListener);
    }

    @Bean
    RedisMessageListenerContainer projectCreateMessageListenerContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisListenerConnectionFactory());
        container.addMessageListener(projectCreateListenerAdapter(), projectCreateTopic());
        return container;
    }

    @Bean
    public ChannelTopic projectCreateTopic() {
        return new ChannelTopic(projectCreateChannel);
    }
}
