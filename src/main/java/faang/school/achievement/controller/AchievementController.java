package faang.school.achievement.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Validated
@RequiredArgsConstructor
@Controller
@RequestMapping("/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/filtered")
    public List<AchievementDto> getAllAchievement(@RequestBody AchievementFilterDto filters) {
        return achievementService.getAllAchievement(filters);
    }

    @GetMapping("/{userId}/all")
    public List<UserAchievementDto> getAllAchievementByUser(@PathVariable Long userId) {
        return achievementService.getAllAchievementByUser(userId);
    }

    @Async
    @Cacheable(cacheNames = "achievementId", key = "#achievementId")
    @GetMapping("/{achievementId}")
    public AchievementDto getAchievement(@PathVariable Long achievementId) {
        return achievementService.getAchievementById(achievementId);
    }

    @GetMapping("/{userId}/inProgress")
    public List<AchievementProgressDto> getAchievementInProgressOfUser(@PathVariable Long userId) {
        return achievementService.getAchievementInProgressOfUser(userId);
    }
}
