package faang.school.achievement.handler;

public interface EventHandler<T> {
    void handle(T event);

    boolean examinationEvent(T event);
}
