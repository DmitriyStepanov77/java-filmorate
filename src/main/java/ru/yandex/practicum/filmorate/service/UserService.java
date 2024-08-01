package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbStorage.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.DtoMappers.UserDtoMapper;
import ru.yandex.practicum.filmorate.storage.DtoModel.UserDto;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    @Autowired
    private final FriendDbStorage friendDbStorage;

    public UserDto addUser(User user) {
        return UserDtoMapper.mapToUserDTO(userStorage.addUser(user));
    }

    public Collection<UserDto> getUsers() {
        return userStorage.getUsers().stream().map(UserDtoMapper::mapToUserDTO).collect(Collectors.toList());
    }

    public UserDto updateUser(User user) {
        return UserDtoMapper.mapToUserDTO(userStorage.updateUser(user));
    }

    public UserDto getUser(long userId) {
        return UserDtoMapper.mapToUserDTO(userStorage.getUser(userId));
    }

    public UserDto addFriend(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);
        friendDbStorage.addFriend(userId, friendId);
        log.info("Пользователь с ID = {} добавил в друзья пользователя с ID = {}", userId, friendId);
        return UserDtoMapper.mapToUserDTO(userStorage.getUser(userId));
    }

    public UserDto deleteFriend(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);
        friendDbStorage.deleteFriend(userId, friendId);
        log.info("Пользователь с ID = {} удалил из друзей пользователя с ID = {}", userId, friendId);
        return UserDtoMapper.mapToUserDTO(userStorage.getUser(userId));
    }

    public Collection<UserDto> getFriends(long userId) {
        return userStorage.getUser(userId).getFriends().stream()
                .map(Friend::getId)
                .map(userStorage::getUser)
                .map(UserDtoMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public Collection<UserDto> getCommonFriends(long userId, long friendId) {
        Set<Friend> userFriends = userStorage.getUser(userId).getFriends();
        return userFriends.stream()
                .filter(userStorage.getUser(friendId).getFriends()::contains)
                .map(Friend::getId)
                .map(userStorage::getUser)
                .map(UserDtoMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    private void checkUser(Long userId) {
        userStorage.getUser(userId);
    }
}
