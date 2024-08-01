package ru.yandex.practicum.filmorate.storage.MemoryStorage;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.Check;

import java.util.*;

@Log4j2
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        Check.checkIdSet(user.getId(), users.keySet(), "Пользователь с таким ID уже существует");
        if (user.getId() == 0L)
            user.setId(setId());
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Добавлен пользователь {} с ID = {}", user.getLogin(), user.getId());
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.get(user.getId()) == null)
            throw new NotFoundException("Пользователь с id=" + user.getId() + " отсутствует.");
        users.put(user.getId(), user);
        log.info("Обновлен пользователь {} с ID = {}", user.getName(), user.getId());
        return user;
    }

    @Override
    public User getUser(long id) {
        if (users.get(id) == null)
            throw new NotFoundException("Пользователь с id=" + id + " отсутствует.");
        return users.get(id);
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    private long setId() {
        Optional<Long> id = users.keySet().stream().max(Comparator.naturalOrder());
        return id.map(aLong -> aLong + 1).orElse(1L);
    }
}
