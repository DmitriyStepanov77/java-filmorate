package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbStorage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.DbStorage.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.DbStorage.UserDbStorage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeStorageTest {
    private final UserDbStorage userStorage;
    private final LikeDbStorage likeStorage;
    private final FilmDbStorage filmStorage;

    private final JdbcTemplate jdbc;

    private static final String DELETE_LIKES = "DELETE FROM Likes";

    User user1 = new User();
    User user2 = new User();
    Film film = new Film();

    @BeforeEach
    public void beforeEach() {
        user1.setLogin("Login1");
        user1.setName("Name1");
        user1.setBirthday(LocalDate.parse("1946-08-20"));
        user1.setEmail("12345678@mail.ru");

        user2.setLogin("Login2");
        user2.setName("Name2");
        user2.setBirthday(LocalDate.parse("1946-08-20"));
        user2.setEmail("12345678@mail.ru");

        film.setName("Name1");
        film.setDescription("Description1");
        film.setReleaseDate(LocalDate.parse("1946-08-20"));
        MPA mpa = new MPA();
        mpa.setId(1);
        film.setMpa(mpa);

        userStorage.addUser(user1);
        userStorage.addUser(user2);
        filmStorage.addFilm(film);
    }

    @AfterEach
    public void afterEach() {
        jdbc.update(DELETE_LIKES);
    }



    @Test
    public void testAddAndGetLike() {
        likeStorage.addLike(film.getId(), user1.getId());
        likeStorage.addLike(film.getId(), user2.getId());
        assertThat(likeStorage.getLikes(film.getId()).size()).isEqualTo(2);
    }

    @Test
    public void testDeleteLike() {
        likeStorage.addLike(film.getId(), user1.getId());
        likeStorage.addLike(film.getId(), user2.getId());
        assertThat(likeStorage.getLikes(film.getId()).size()).isEqualTo(2);

        likeStorage.deleteLike(film.getId(), user2.getId());
        assertThat(likeStorage.getLikes(film.getId()).size()).isEqualTo(1);
    }

}
