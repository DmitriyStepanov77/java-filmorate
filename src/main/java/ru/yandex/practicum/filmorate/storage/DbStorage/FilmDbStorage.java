package ru.yandex.practicum.filmorate.storage.DbStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.RowMappers.GenreRowMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Repository
public class FilmDbStorage extends BaseDb<Film> implements FilmStorage {
    private static final String INSERT_FILM = "INSERT INTO Films(Name, " +
            "Description, " +
            "ReleaseDate, " +
            "Duration, " +
            "MPA_ID) VALUES(?, ?, ?, ?, ?)";
    private static final String INSERT_GENRES_FILMS = "INSERT INTO Genres_Films VALUES(?, ?)";
    private static final String UPDATE_FILM = "UPDATE Films SET Name = ?," +
            "Description = ?, " +
            "ReleaseDate = ?, " +
            "Duration = ?, " +
            "MPA_ID = ? WHERE Film_ID = ?";
    private static final String SELECT_FILM = "SELECT f.*, m.Name MPA_Name " +
            "FROM Films f " +
            "JOIN MPA m ON f.MPA_ID = m.MPA_ID " +
            "WHERE Film_ID = ?";
    private static final String SELECT_FILMS = "SELECT f.*, m.Name MPA_Name " +
            "FROM Films f " +
            "JOIN MPA m ON f.MPA_ID = m.MPA_ID ";
    private static final String SELECT_GENRES_FILMS = "SELECT g.Genre_ID, g.Name " +
            "FROM Genres_Films gf " +
            "JOIN Genre g ON gf.Genre_ID = g.Genre_ID " +
            "WHERE Film_ID = ?";
    @Autowired
    private LikeDbStorage likeDbStorage;
    @Autowired
    private GenreDbStorage genreDbStorage;
    @Autowired
    private MpaDbStorage mpaDbStorage;

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Film addFilm(Film film) {
        checkGenreAndMpa(film);
        long id = insert(INSERT_FILM, film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        for (Genre g : film.getGenres()) {
            update(INSERT_GENRES_FILMS, id, g.getId());
        }
        film.setId(id);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        checkGenreAndMpa(film);
        getFilm(film.getId()); //Проверяем, существует ли обновляемый фильм
        update(UPDATE_FILM, film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public Film getFilm(long id) {
        Optional<Film> optionalFilm = findOne(SELECT_FILM, id);
        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            film.setLikes(new HashSet<>(likeDbStorage.getLikes(film.getId())));
            film.setGenres(new HashSet<>(jdbc.query(SELECT_GENRES_FILMS, new GenreRowMapper(), film.getId())));
            return film;
        } else
            throw new NotFoundException("Фильм с данным ID не найден");
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> collection = findMany(SELECT_FILMS);
        for (Film f : collection) {
            f.setLikes(new HashSet<>(likeDbStorage.getLikes(f.getId())));
            f.setGenres(new HashSet<>(jdbc.query(SELECT_GENRES_FILMS, new GenreRowMapper(), f.getId())));
        }
        return collection;
    }

    private void checkGenreAndMpa(Film film) { //Проверяем, существуют ли жанры и рейтинг фильма
        try {
            mpaDbStorage.getMPA(film.getMpa().getId());
            for (Genre g : film.getGenres()) {
                genreDbStorage.getGenre(g.getId());
            }
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
