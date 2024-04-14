package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {
AchievementDto toDtoUserAchievements(Achievement achievement);

List<AchievementDto> toDtoAchievements (List<Achievement> achievements);

@Mapping(source = "achievement" , target = "achievementId",qualifiedByName = "toAchievementId")
List<UserAchievementDto> toDtoUserAchievements(List<UserAchievement> userAchievements);
@Named("toAchievementId")
default List<Long> toAchievementId(List<Achievement> achievements) {
    return achievements.stream().map(Achievement::getId).toList();
}

Achievement toEntity(AchievementDto achievementDto);
}
