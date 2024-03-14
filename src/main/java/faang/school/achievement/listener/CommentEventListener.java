package faang.school.achievement.listener;

import faang.school.achievement.dto.CommentEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CommentEventListener extends AbstractEventListener<CommentEventDto> {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEventDto event = getEvent(message, CommentEventDto.class);
        log.info("Start processing an incoming event - {}", event);
        processEvent(event.getAuthorId());
    }
}