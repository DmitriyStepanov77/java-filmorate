package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.DbStorage.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.DtoModel.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DtoMappers.FilmDtoMapper;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class FilmService {
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    @Autowired
    private final UserService userService;
    @Autowired
    private final LikeDbStorage likeDbStorage;

    public FilmDto addFilm(Film film) {
        return FilmDtoMapper.mapToFilmDTO(filmStorage.addFilm(film));
    }

    public FilmDto updateFilm(Film film) {
        checkFilm(film.getId()); //Проверяем, существует ли обновляемый фильм
        return FilmDtoMapper.mapToFilmDTO(filmStorage.updateFilm(film));
    }

    public Collection<FilmDto> getFilms() {
        System.out.println(filmStorage.getFilms());
        return filmStorage.getFilms().stream()
                .map(FilmDtoMapper::mapToFilmDTO)
                .sorted(Comparator.comparingLong(FilmDto::getId))
                .collect(Collectors.toList());
    }

    public FilmDto getFilm(long id) {
        return FilmDtoMapper.mapToFilmDTO(filmStorage.getFilm(id));
    }

    public FilmDto addLike(long filmId, long userId) {
        userService.checkUser(userId); //Проверяем, существует ли пользователь, поставивший лайк
        checkFilm(filmId); //Проверяем, существует ли фильм
        likeDbStorage.addLike(filmId, userId);
        log.info("Пользователь с ID = {} лайкнул фильм с ID = {}", userId, filmId);
        return FilmDtoMapper.mapToFilmDTO(filmStorage.getFilm(filmId));
    }


    public FilmDto deleteLike(long filmId, long userId) {
        if (!likeDbStorage.getLikes(filmId).contains(userId)) //Проверяем, существует ли лайк
            throw new ValidationException("У фильма с ID = " + filmId +
                    " отсутствует лайк от пользователя с ID = " + userId);
        likeDbStorage.deleteLike(filmId, userId);
        log.info("Пользователь с ID = {} удалил лайк фильму с ID = {}", userId, filmId);
        return FilmDtoMapper.mapToFilmDTO(filmStorage.getFilm(filmId));
    }

    public Collection<FilmDto> popular(long count) {
        return filmStorage.getFilms().stream()
                .filter(film -> film.getLikes() != null)
                .map(FilmDtoMapper::mapToFilmDTO)
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .toList();
    }

    void checkFilm(Long filmId) {
        filmStorage.getFilm(filmId);
    }
}
