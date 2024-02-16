package faang.school.achievement.exception;

import org.webjars.NotFoundException;

public class EntityNotFoundException extends NotFoundException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}