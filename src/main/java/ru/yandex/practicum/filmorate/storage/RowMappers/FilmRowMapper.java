package ru.yandex.practicum.filmorate.storage.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("Film_ID"));
        film.setName(rs.getString("Name"));
        film.setDescription(rs.getString("Description"));
        film.setReleaseDate(rs.getTimestamp("ReleaseDate").toLocalDateTime().toLocalDate());
        film.setDuration(rs.getInt("Duration"));
        MPA mpa = new MPA();
        mpa.setId(rs.getInt("MPA_ID"));
        mpa.setName(rs.getString("MPA_Name"));
        film.setMpa(mpa);
        return film;
    }
}
