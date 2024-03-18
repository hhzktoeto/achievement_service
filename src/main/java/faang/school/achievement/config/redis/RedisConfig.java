package faang.school.achievement.config.redis;

import faang.school.achievement.listener.MentorshipEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String hostName;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channel.mentorship}")
    private String mentorshipChannel;

    private final MentorshipEventListener mentorshipEventListener;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostName, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public ChannelTopic mentorshipTopic() {
        return new ChannelTopic(mentorshipChannel);
    }

    @Bean
    public MessageListenerAdapter mentorshipListener() {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(mentorshipListener(), mentorshipTopic());
        return container;
    }

}
