package faang.school.achievement.config;

import faang.school.achievement.listener.CommentEventListener;
import faang.school.achievement.listener.TaskCompletedEventListener;
import faang.school.achievement.listener.GoalEventListener;
import lombok.RequiredArgsConstructor;
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

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.channel.task}")
    private String taskCompletedEventChannel;

    @Value("${spring.data.redis.channel.comment}")
    private String commentChannelName;

    @Value("${spring.data.redis.channel.goal_created}")
    private String goalChannelName;

    private final GoalEventListener goalEventListener;
    private final TaskCompletedEventListener taskCompletedEventListener;
    private final CommentEventListener commentEventListener;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost,redisPort);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    MessageListenerAdapter taskCompletedMessageListener(){
        return new MessageListenerAdapter(taskCompletedEventListener);
    }

    @Bean
    ChannelTopic taskCompletedTopic(){
        return new ChannelTopic(taskCompletedEventChannel);
    }

    @Bean
    ChannelTopic commentChannel() {
        return new ChannelTopic(commentChannelName);
    }

    @Bean
    MessageListenerAdapter commentListener() {
        return new MessageListenerAdapter(commentEventListener);
    }

    @Bean
    ChannelTopic goalChannel() {
        return new ChannelTopic(goalChannelName);
    }

    @Bean
    MessageListenerAdapter goalListener() {
        return new MessageListenerAdapter(goalEventListener);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(taskCompletedMessageListener(),taskCompletedTopic());
        container.addMessageListener(commentListener(), commentChannel());
        container.addMessageListener(goalListener(), goalChannel());
        return container;
    }
}