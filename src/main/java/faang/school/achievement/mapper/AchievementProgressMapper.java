package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementProgressMapper {

    AchievementProgressDto toDto(AchievementProgress achievementProgress);

    @Mapping(source = "achievement", target = "achievementId" , qualifiedByName = "toAchievementId")
    List<AchievementProgressDto> toListAchievementProgressDto(List<AchievementProgress> achievementProgress);

    @Named("toAchievementId")
    default List<Long> toAchievementId(List<Achievement> achievement) {
        return achievement.stream()
                .map(Achievement::getId)
                .toList();
    }
}
