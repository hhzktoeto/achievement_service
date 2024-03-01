package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.handler.follower.AbstractFollowerEventHandler;
import faang.school.achievement.handler.follower.achievments.CelebrityHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowerEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<AbstractFollowerEventHandler<FollowerEvent>> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            FollowerEvent event = objectMapper.readValue(message.getBody(), FollowerEvent.class);
            for (AbstractFollowerEventHandler<FollowerEvent> handler : handlers) {
                handler.handle(event);
            }
        } catch (IOException e) {
            log.error("Unsuccessful convert Json to Data");
            throw new RuntimeException(e.getMessage());
        }
    }
}
