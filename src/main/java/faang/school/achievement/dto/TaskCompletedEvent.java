package faang.school.achievement.dto;

import lombok.Data;

@Data
public class TaskCompletedEvent {
    private long userId;
    private long projectId;
    private long taskId;
}
