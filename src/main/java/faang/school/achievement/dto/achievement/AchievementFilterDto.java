package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementFilterDto {
    @Size(max = 255, message = "Achievement title pattern can't be longer than 255 characters")
    private String title;
    @Size(max = 255, message = "Achievement description pattern can't be longer than 255 characters")
    private String description;
    private Rarity rarity;
}
