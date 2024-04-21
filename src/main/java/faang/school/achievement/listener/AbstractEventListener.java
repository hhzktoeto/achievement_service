package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.service.event.EventHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T> {

    protected final List<EventHandler<T>> commentHandlers;
    protected final ObjectMapper objectMapper;
}
