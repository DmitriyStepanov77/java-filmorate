package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.FilmReleaseDate;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    long id;
    @NotEmpty
    String name;
    @Size(min = 0, max = 200)
    String description;
    @FilmReleaseDate
    LocalDate releaseDate;
    @Positive
    Duration duration;
}
