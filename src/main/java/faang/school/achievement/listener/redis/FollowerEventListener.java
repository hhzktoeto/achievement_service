package faang.school.achievement.listener.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.follower.FollowerEvent;
import faang.school.achievement.service.event.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FollowerEventListener extends AbstractEventListener<FollowerEvent> {

    public FollowerEventListener(ObjectMapper objectMapper, List<EventHandler<FollowerEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, FollowerEvent.class);
    }
}
