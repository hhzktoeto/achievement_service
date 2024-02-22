package faang.school.achievement.handler;

import org.springframework.scheduling.annotation.Async;

public interface EventHandler<T> {
    @Async("myExecutorService")
    void handle(Long userId);

    Class<?> supportsEventType();
}
