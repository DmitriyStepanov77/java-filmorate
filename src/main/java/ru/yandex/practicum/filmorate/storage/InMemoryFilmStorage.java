package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.Check;

import java.util.*;

@Log4j2
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film addFilm(Film film) {
        Check.checkIdSet(film.getId(), films.keySet(), "Фильм с таким ID уже существует");
        if (film.getId() == 0L)
            film.setId(setId());
        films.put(film.getId(), film);
        log.info("Добавлен фильм {} с ID = {}", film.getName(), film.getId());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.get(film.getId()) == null)
            throw new NotFoundException("Фильм с id=" + film.getId() + " отсутствует.");
        films.put(film.getId(), film);
        log.info("Обновлен фильм {} с ID = {}", film.getName(), film.getId());
        return film;
    }

    @Override
    public Film getFilm(long id) {
        if (films.get(id) == null)
            throw new NotFoundException("Фильм с id=" + id + " отсутствует.");
        return films.get(id);
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    private long setId() {
        Optional<Long> id = films.keySet().stream().max(Comparator.naturalOrder());
        return id.map(aLong -> aLong + 1).orElse(1L);
    }
}
