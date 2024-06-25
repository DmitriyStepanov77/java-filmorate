package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            log.warn("Фильм с ID = {} уже существует", film.getId());
            throw new ValidationException("Фильм с таким ID уже существует.");
        }
        if (film.getId() == 0L)
            film.setId(setId());
        films.put(film.getId(), film);
        log.info("Добавлен фильм {} с ID = {}", film.getName(), film.getId());
        return film;
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        if (film.getId() == 0L || !films.containsKey(film.getId()))
            throw new ValidationException("Неверный ID фильма");
        films.put(film.getId(), film);
        log.info("Обновлен фильм {} с ID = {}", film.getName(), film.getId());
        return film;
    }

    private long setId() {
        Optional<Long> id = films.keySet().stream().max(Comparator.naturalOrder());
        return id.map(aLong -> aLong + 1).orElse(1L);
    }
}
