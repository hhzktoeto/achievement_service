package faang.school.achievement.listener;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public interface EventListener implements MessageListener {
}
