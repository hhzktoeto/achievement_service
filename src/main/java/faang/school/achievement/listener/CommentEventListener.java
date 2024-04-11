package faang.school.achievement.listener;

import faang.school.achievement.event.comment.CommentEvent;
import faang.school.achievement.service.event.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final List<EventHandler<CommentEvent>> commentHandlers;

    @KafkaListener(topics = "${spring.data.kafka.channels.comment-channel.name}", groupId = "${spring.data.kafka.group-id}")
    public void listen(CommentEvent event) {
        commentHandlers.forEach(handler -> handler.handle(event));
        log.info("Handled comment event: {}", event);
    }
}
