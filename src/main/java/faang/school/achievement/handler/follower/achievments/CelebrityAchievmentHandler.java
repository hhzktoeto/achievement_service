package faang.school.achievement.handler.follower.achievments;

import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.handler.follower.AbstractFollowerEventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchivementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CelebrityAchievmentHandler extends AbstractFollowerEventHandler<FollowerEvent> {

    private final AchivementService achivementService;

    @Override
    public void handle(Long userId) {

    }


}
