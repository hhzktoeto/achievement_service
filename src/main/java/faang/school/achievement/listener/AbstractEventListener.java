package faang.school.achievement.listener;

import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {

    protected final MapperJsonToData mapperJsonToData;
    private final List<EventHandler<T>> handlers;

    public void handle(long userId) {
        handlers.forEach(eventHandler -> eventHandler.handle(userId));
    }
}
