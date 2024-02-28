package faang.school.achievement.service;

import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchivementService {

    private final AchievementRepository achievementRepository;

}
