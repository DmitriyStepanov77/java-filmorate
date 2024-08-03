package ru.yandex.practicum.filmorate.storage.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("User_ID"));
        user.setName(rs.getString("Name"));
        user.setLogin(rs.getString("Login"));
        user.setBirthday(rs.getTimestamp("Birthday").toLocalDateTime().toLocalDate());
        return user;
    }
}
