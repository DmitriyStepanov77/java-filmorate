package ru.yandex.practicum.filmorate.storage.DtoMappers;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.DtoModel.GenreDto;

public class GenreDtoMapper {
    public static GenreDto mapToGenreDTO(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }
}
