package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAchievementMapper {

    UserAchievementDto toDto(UserAchievement userAchievement);

    @Mapping(source = "achievement", target = "achievementId", qualifiedByName = "toAchievementId")
    List<UserAchievementDto> toDtoUserAchievementsList(List<UserAchievement> userAchievements);

    @Named("toAchievementId")
    default List<Long> toAchievementId(List<Achievement> achievement) {
        return achievement.stream().map(Achievement::getId).toList();
    }
}
