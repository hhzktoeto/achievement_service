package faang.school.achievement.listener;

import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipEventListener extends AbstractEventListener<MentorshipStartEvent> {
    private final List<EventHandler> eventHandlers;

    public MentorshipEventListener(List<EventHandler> eventHandlers) {
        super(MentorshipStartEvent.class);
        this.eventHandlers = eventHandlers;
    }

    @Override
    protected void processEvent(MentorshipStartEvent event) {
        eventHandlers.forEach(handler -> handler.handle(event.getMenteeId()));
    }
}
