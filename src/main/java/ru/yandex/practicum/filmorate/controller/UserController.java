package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.Check;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
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

    @PutMapping
    public User putUser(@Valid @RequestBody User user) {
        Check.checkIdUpdate(user.getId(), users.keySet(), "Неверный ID пользователя");
        users.put(user.getId(), user);
        log.info("Обновлен пользователь {} с ID = {}", user.getLogin(), user.getId());
        return user;
    }

    private long setId() {
        Optional<Long> id = users.keySet().stream().max(Comparator.naturalOrder());
        return id.map(aLong -> aLong + 1).orElse(1L);
    }
}
