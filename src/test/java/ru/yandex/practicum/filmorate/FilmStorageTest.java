package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.DbStorage.FilmDbStorage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmStorageTest {
    private final FilmDbStorage filmStorage;
    private final JdbcTemplate jdbc;
    private static final String DELETE_FILMS = "DELETE FROM Films";
    Film film = new Film();

    @BeforeEach
    public void before() {
        film.setName("Name1");
        film.setDescription("Description1");
        film.setReleaseDate(LocalDate.parse("1946-08-20"));
        MPA mpa = new MPA();
        mpa.setId(1);
        film.setMpa(mpa);
        jdbc.update(DELETE_FILMS);
    }

    @Test
    public void testAddAndGetFilm() {
        filmStorage.addFilm(film);
        assertThat(filmStorage.getFilm(film.getId()).getName()).isEqualTo("Name1");
    }

    @Test
    public void testUpdateFilm() {
        filmStorage.addFilm(film);
        film.setName("Name2");
        filmStorage.updateFilm(film);
        assertThat(filmStorage.getFilm(film.getId()).getName()).isEqualTo("Name2");
    }

    @Test
    public void getAllFilms() {
        filmStorage.addFilm(film);
        filmStorage.addFilm(film);
        filmStorage.addFilm(film);
        filmStorage.addFilm(film);
        assertThat(filmStorage.getFilms().size()).isEqualTo(4);
    }
}
