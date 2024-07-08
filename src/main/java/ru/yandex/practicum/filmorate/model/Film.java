package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.FilmReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {
    private long id;
    @NotEmpty
    private String name;
    @Size(min = 0, max = 200)
    private String description;
    @FilmReleaseDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private final Set<Long> likes = new HashSet<>();
}
