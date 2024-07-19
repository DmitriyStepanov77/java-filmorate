package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    @Autowired
    private final UserService userService;

    public Film addFilm(Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        inMemoryFilmStorage.updateFilm(film);
        return inMemoryFilmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public Film getFilm(long id) {
        return inMemoryFilmStorage.getFilm(id);
    }

    public Film addLike(long filmId, long userId) {
        Set<Long> likes = inMemoryFilmStorage.getFilm(filmId).getLikes();
        userService.getUser(userId);
        likes.add(userId);
        log.info("Пользователь с ID = {} лайкнул фильм с ID = {}", userId, filmId);
        return inMemoryFilmStorage.getFilm(filmId);
    }

    public Film deleteLike(long filmId, long userId) {
        Set<Long> likes = inMemoryFilmStorage.getFilm(filmId).getLikes();
        userService.getUser(userId);
        likes.remove(userId);
        log.info("Пользователь с ID = {} удалил лайк фильму с ID = {}", userId, filmId);
        return inMemoryFilmStorage.getFilm(filmId);
    }

    public Collection<Film> popular(long count) {
        return inMemoryFilmStorage.getFilms().stream()
                .filter(film -> film.getLikes() != null)
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .toList();
    }
}
