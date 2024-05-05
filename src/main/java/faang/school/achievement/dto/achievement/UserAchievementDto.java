package faang.school.achievement.dto.achievement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAchievementDto {

    private long id;
    private List<Long> achievementId;
    private long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
