package ru.yandex.practicum.filmorate.storage.DbStorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friend;

import java.util.Collection;

@Repository
public class FriendDbStorage extends BaseDb<Friend> {
    private static final String INSERT_FRIEND = "INSERT INTO Friends(User_ID, " +
            "Friend_ID) VALUES(?, ?)";
    private static final String DELETE_FRIEND = "DELETE FROM Friends WHERE User_ID = ? AND Friend_ID = ?";
    private static final String SELECT_FRIENDS = "SELECT Friend_ID, Status FROM Friends WHERE User_ID = ?";

    public FriendDbStorage(JdbcTemplate jdbc, RowMapper<Friend> mapper) {
        super(jdbc, mapper);
    }

    public void addFriend(Long userId, Long friendId) {
        update(INSERT_FRIEND, userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        delete(DELETE_FRIEND, userId, friendId);
    }

    public Collection<Friend> getFriends(Long userId) {
        return findMany(SELECT_FRIENDS, userId);
    }
}
