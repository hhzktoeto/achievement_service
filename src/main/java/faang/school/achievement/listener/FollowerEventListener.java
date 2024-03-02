package faang.school.achievement.listener;

import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FollowerEventListener extends AbstractEventListener<FollowerEvent> {

    @Autowired
    public FollowerEventListener(MapperJsonToData mapperJsonToData,
                                 List<EventHandler<FollowerEvent>> handlers) {
        super(mapperJsonToData, handlers);
    }

    @Async
    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        FollowerEvent event = mapperJsonToData.convertJsonToEvent(message, FollowerEvent.class);
        handle(event.getFolloweeId());
    }

}
