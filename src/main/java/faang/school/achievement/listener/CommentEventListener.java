package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.comment.CommentEvent;
import faang.school.achievement.service.event.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent> {

    public CommentEventListener(List<EventHandler<CommentEvent>> commentHandlers, ObjectMapper objectMapper) {
        super(objectMapper, commentHandlers, CommentEvent.class);
    }

    @KafkaListener(topics = "${spring.data.kafka.channels.comment-channel.name}", groupId = "${spring.data.kafka.group-id}")
    public void listen(String event) {
        try {
            CommentEvent commentEvent = objectMapper.readValue(event, CommentEvent.class);
            handlers.forEach(handler -> handler.handle(commentEvent));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
