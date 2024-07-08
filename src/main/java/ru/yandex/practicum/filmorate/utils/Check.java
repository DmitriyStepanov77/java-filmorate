package ru.yandex.practicum.filmorate.utils;

import lombok.extern.log4j.Log4j2;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Set;

@Log4j2
public class Check {
    public static void checkIdSet(long id, Set<Long> values, String message) {
        if (values.contains(id)) {
            log.warn(message);
            throw new ValidationException(message);
        }
    }

    public static void checkIdUpdate(long id, Set<Long> values, String message) {
        if (id == 0L || !values.contains(id)) {
            log.warn(message);
            throw new NotFoundException(message);
        }
    }
}

