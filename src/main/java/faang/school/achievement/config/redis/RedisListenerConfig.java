package faang.school.achievement.config.redis;

import faang.school.achievement.listener.ProjectCreateEventListener;
import faang.school.achievement.listener.redis.FollowerEventListener;
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

    private final FollowerEventListener followerEventListener;
    private final ProjectCreateEventListener projectCreateEventListener;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channel.project-create-channel}")
    private String projectCreateChannel;

    @Value("${spring.data.redis.channel.follower}")
    private String followerTopic;

    @Bean
    public ChannelTopic followerTopic() {
        return new ChannelTopic(followerTopic);
    }

    @Bean
    public ChannelTopic projectCreateTopic() {
        return new ChannelTopic(projectCreateChannel);
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    RedisTemplate<String, Object> redisListenerTemplate() {
        final RedisTemplate<String, Object> listenerTemplate = new RedisTemplate<String, Object>();
        listenerTemplate.setConnectionFactory(redisConnectionFactory());
        listenerTemplate.setKeySerializer(new StringRedisSerializer());
        listenerTemplate.setValueSerializer(new StringRedisSerializer());
        return listenerTemplate;
    }

    @Bean
    public MessageListenerAdapter followerListenerAdapter() {
        return new MessageListenerAdapter(followerEventListener);
    }

    @Bean
    MessageListenerAdapter projectCreateListenerAdapter() {
        return new MessageListenerAdapter(projectCreateEventListener);
    }

    @Bean
    public RedisMessageListenerContainer followerMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(followerListenerAdapter(), followerTopic());
        container.addMessageListener(projectCreateListenerAdapter(), projectCreateTopic());
        return container;
    }
}
