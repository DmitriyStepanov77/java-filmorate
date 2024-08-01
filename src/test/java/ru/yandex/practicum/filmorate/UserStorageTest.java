package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbStorage.UserDbStorage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserStorageTest {
    private final UserDbStorage userStorage;
    private final JdbcTemplate jdbc;
    private static final String DELETE_USERS = "DELETE FROM Users";
    User user = new User();

    @BeforeEach
    public void beforeEach() {
        user.setLogin("Login1");
        user.setName("Name1");
        user.setBirthday(LocalDate.parse("1946-08-20"));
        user.setEmail("12345678@mail.ru");
    }

    @AfterEach
    public void afterEach() {
        jdbc.update(DELETE_USERS);
    }

    @Test
    public void testAddAndGetUser() {
        userStorage.addUser(user);
        assertThat(userStorage.getUser(1).getName()).isEqualTo("Name1");

    }

    @Test
    public void testUpdateUser() {
        userStorage.addUser(user);
        user.setName("Name2");
        userStorage.updateUser(user);
        assertThat(userStorage.getUser(user.getId()).getName()).isEqualTo("Name2");
    }

    @Test
    public void getAllUsers() {
        userStorage.addUser(user);
        userStorage.addUser(user);
        userStorage.addUser(user);
        assertThat(userStorage.getUsers().size()).isEqualTo(3);
    }
}
