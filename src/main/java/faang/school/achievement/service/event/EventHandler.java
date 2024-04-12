package faang.school.achievement.service.event;

public interface EventHandler<T> {
    void handle(T event);
}
