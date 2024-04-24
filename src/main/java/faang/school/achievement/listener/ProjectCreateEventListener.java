package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.service.event.businessman.BusinessmanAchievementHandler;
import faang.school.achievement.service.event.EventHandler;
import lombok.NonNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectCreateEventListener extends AbstractEventListener<ProjectCreateEvent> implements MessageListener {
    private BusinessmanAchievementHandler businessmanAchievementHandler;

    public ProjectCreateEventListener(ObjectMapper objectMapper, List<EventHandler<ProjectCreateEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        ProjectCreateEvent event = mapEvent(message, ProjectCreateEvent.class);
        businessmanAchievementHandler.handle(event);
    }
}
