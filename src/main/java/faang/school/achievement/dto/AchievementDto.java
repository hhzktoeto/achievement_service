package faang.school.achievement.dto;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
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
public class AchievementDto {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;
    private List<UserAchievement> userAchievements;
    private List<AchievementProgress> progresses;
    private long points;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
