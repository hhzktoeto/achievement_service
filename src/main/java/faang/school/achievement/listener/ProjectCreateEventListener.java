package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.service.event.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectCreateEventListener extends AbstractEventListener<ProjectCreateEvent> {

    public ProjectCreateEventListener(ObjectMapper objectMapper, List<EventHandler<ProjectCreateEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, ProjectCreateEvent.class);
    }
}