package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.DtoModel.GenreDto;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public Collection<GenreDto> getFilms() {
        return genreService.getGenres();
    }

    @GetMapping("/{genreId}")
    public GenreDto getFilm(@PathVariable int genreId) {
        return genreService.getGenre(genreId);
    }


}
