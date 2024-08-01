package ru.yandex.practicum.filmorate.storage.DbStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Repository
public class UserDbStorage extends BaseDb<User> implements UserStorage {
    private static final String INSERT_USER = "INSERT INTO Users(Login, " +
            "Email, " +
            "Name, " +
            "Birthday) VALUES(?, ?, ?, ?)";
    private static final String INSERT_FRIENDS = "INSERT INTO Friends VALUES(?, ?)";
    private static final String UPDATE_USER = "UPDATE Users SET Login = ?," +
            "Email = ?, " +
            "Name = ?, " +
            "Birthday = ? WHERE User_ID = ?";
    private static final String SELECT_USER = "SELECT * FROM Users WHERE User_ID = ?";
    private static final String SELECT_USERS = "SELECT * FROM Users";

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Autowired
    private FriendDbStorage friendDbStorage;

    @Override
    public User addUser(User user) {
        long id = insert(INSERT_USER, user.getLogin(),
                user.getEmail(),
                user.getName(),
                user.getBirthday());
        for (Friend f : user.getFriends()) {
            update(INSERT_FRIENDS, id, f.getId());
        }
        user.setId(id);
        return user;
    }

    @Override
    public User updateUser(User user) {
        getUser(user.getId()); //Проверяем, существует ли обновляемый пользователь
        update(UPDATE_USER, user.getLogin(),
                user.getEmail(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public User getUser(long id) {
        Optional<User> optionalUser = findOne(SELECT_USER, id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFriends(new HashSet<>(friendDbStorage.getFriends(user.getId())));
            return user;
        } else
            throw new NotFoundException("Пользователь с данным ID не найден");
    }

    @Override
    public Collection<User> getUsers() {
        Collection<User> collection = findMany(SELECT_USERS);
        for (User u : collection) {
            u.setFriends(new HashSet<>(friendDbStorage.getFriends(u.getId())));
        }
        return collection;
    }
}
