package ru.yandex.practicum.filmorate.storage.DbStorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class LikeDbStorage extends BaseDb<Long> {
    private static final String INSERT_LIKE = "INSERT INTO Likes(Film_ID, " +
            "User_ID) VALUES(?, ?)";
    private static final String DELETE_LIKE = "DELETE FROM Likes WHERE Film_ID = ? AND User_ID = ?";
    private static final String SELECT_LIKES = "SELECT User_ID FROM Likes WHERE Film_ID = ?";

    public LikeDbStorage(JdbcTemplate jdbc) {
        super(jdbc, (rs, rowNum) -> rs.getLong("User_ID"));
    }

    public void addLike(Long filmId, Long userId) {
        update(INSERT_LIKE, filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        delete(DELETE_LIKE, filmId, userId);
    }

    public Collection<Long> getLikes(Long filmId) {
        return findMany(SELECT_LIKES, filmId);
    }
}
