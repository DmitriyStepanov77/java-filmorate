package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.DbStorage.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.DtoMappers.GenreDtoMapper;
import ru.yandex.practicum.filmorate.storage.DtoModel.GenreDto;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDbStorage genreStorage;

    public Collection<GenreDto> getGenres() {
        return genreStorage.getGenres().stream()
                .map(GenreDtoMapper::mapToGenreDTO)
                .sorted(Comparator.comparingInt(GenreDto::getId))
                .collect(Collectors.toList());
    }

    public GenreDto getGenre(int id) {
        return GenreDtoMapper.mapToGenreDTO(genreStorage.getGenre(id));
    }

}
