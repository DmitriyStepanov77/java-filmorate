package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    public User addUser(User user); //Добавление пользователя

    public User updateUser(User user); //Обновление пользователя

    public User getUser(long id); //Получение пользователя по id

    public Collection<User> getUsers(); //Получение всех пользователей
}
