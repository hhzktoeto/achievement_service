package faang.school.achievement.publisher;

import faang.school.achievement.event.AchievementEvent;

public interface MessagePublisher {
    void publish(AchievementEvent achievementEvent);
}
