package faang.school.achievement.controller;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Validated
@RequiredArgsConstructor
@Controller
@RequestMapping("/achievements")
public class AchievementController {
    private final AchievementService achievementService;
    private final UserContext userContext;

    @PostMapping("/filtered")
    public List<AchievementDto> getAllAchievements(@RequestBody AchievementFilterDto filters) {
        return achievementService.getAllAchievements(filters);
    }

    @GetMapping("/achievements")
    public List<UserAchievementDto> getAllUserAchievements() {
        long userId = userContext.getUserId();
        return achievementService.getAllUserAchievements(userId);
    }

    @GetMapping("/{achievementId}")
    public AchievementDto getAchievement(@PathVariable Long achievementId) {
        return achievementService.getAchievementById(achievementId);
    }

    @GetMapping("/{userId}/progressing")
    public List<AchievementProgressDto> getProgressingUserAchievements() {
        long userId = userContext.getUserId();
        return achievementService.getProgressingUserAchievements(userId);
    }
}
