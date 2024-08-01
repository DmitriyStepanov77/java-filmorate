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

    public void addFriend(Long user_id, Long friend_id) {
        update(INSERT_FRIEND, user_id, friend_id);
    }

    public void deleteFriend(Long user_id, Long friend_id) {
        delete(DELETE_FRIEND, user_id, friend_id);
    }

    public Collection<Friend> getFriends(Long user_id) {
        return findMany(SELECT_FRIENDS, user_id);
    }
}
