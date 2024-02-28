package faang.school.achievement.listener;

import faang.school.achievement.handler.follower.AbstractFollowerEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowerEventListener implements MessageListener {

    private final List<AbstractFollowerEventHandler> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
