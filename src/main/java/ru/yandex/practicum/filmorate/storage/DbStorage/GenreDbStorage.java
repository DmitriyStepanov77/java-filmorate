package ru.yandex.practicum.filmorate.storage.DbStorage;

import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

@Log4j2
@Repository
public class GenreDbStorage extends BaseDb<Genre> {
    private static final String SELECT_GENRE = "SELECT * FROM Genre WHERE Genre_ID = ?";
    private static final String SELECT_GENRES = "SELECT * FROM Genre ORDER BY Genre_ID";

    public GenreDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public Genre getGenre(long id) {
        Optional<Genre> genre = findOne(SELECT_GENRE, id);
        if (genre.isPresent())
            return genre.get();
        else {
            log.error("Жанр с ID = " + id + " не найден");
            throw new NotFoundException("Жанр с ID = " + id + " не найден");
        }
    }

    public Collection<Genre> getGenres() {
        return findMany(SELECT_GENRES);
    }
}
