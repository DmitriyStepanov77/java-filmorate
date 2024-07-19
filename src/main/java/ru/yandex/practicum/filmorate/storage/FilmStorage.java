package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    public Film addFilm(Film film); //Добавление фильма

    public Film updateFilm(Film film); //Обновление фильма

    public Film getFilm(long id); //Получение фильма по id

    public Collection<Film> getFilms(); //Получение всех фильмов
}
