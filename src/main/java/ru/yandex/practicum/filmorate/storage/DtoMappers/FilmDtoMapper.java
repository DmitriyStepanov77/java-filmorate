package ru.yandex.practicum.filmorate.storage.DtoMappers;

import ru.yandex.practicum.filmorate.storage.DtoModel.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmDtoMapper {
    public static FilmDto mapToFilmDTO(Film film) {
        FilmDto dto = new FilmDto();
        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setDuration(film.getDuration());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setLikes(film.getLikes());
        dto.setMpa(film.getMpa());
        dto.setGenres(film.getGenres());
        return dto;
    }
}
