package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage inMemoryUserStorage;

    public User addUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public User getUser(long userId) {
        return inMemoryUserStorage.getUser(userId);
    }

    public User addFriend(long userId, long friendId) {
        User user = inMemoryUserStorage.getUser(userId);
        User friend = inMemoryUserStorage.getUser(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        log.info("Пользователь с ID = {} добавил в друзья пользователя с ID = {}", userId, friendId);
        return user;
    }

    public User deleteFriend(long userId, long friendId) {
        User user = inMemoryUserStorage.getUser(userId);
        User friend = inMemoryUserStorage.getUser(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.info("Пользователь с ID = {} удалил из друзей пользователя с ID = {}", userId, friendId);
        return user;
    }

    public Collection<User> getFriends(long userId) {
        return inMemoryUserStorage.getUser(userId).getFriends().stream()
                .map(inMemoryUserStorage::getUser)
                .toList();
    }

    public Collection<User> getCommonFriends(long userId, long friendId) {
        Set<Long> userFriends = inMemoryUserStorage.getUser(userId).getFriends();
        return userFriends.stream()
                .filter(inMemoryUserStorage.getUser(friendId).getFriends()::contains)
                .map(inMemoryUserStorage::getUser)
                .toList();
    }
}
