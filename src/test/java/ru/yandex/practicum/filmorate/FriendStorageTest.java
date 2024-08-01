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
import ru.yandex.practicum.filmorate.storage.DbStorage.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.DbStorage.UserDbStorage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendStorageTest {
    private final UserDbStorage userStorage;
    private final FriendDbStorage friendStorage;

    private final JdbcTemplate jdbc;

    private static final String DELETE_FRIENDS = "DELETE FROM Friends";

    User user1 = new User();
    User user2 = new User();

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

        userStorage.addUser(user1);
        userStorage.addUser(user2);
    }

    @AfterEach
    public void afterEach() {
        jdbc.update(DELETE_FRIENDS);
    }

    @Test
    public void testAddAndGetFriend() {
        friendStorage.addFriend(user1.getId(), user2.getId());
        assertThat(friendStorage.getFriends(user1.getId()).stream().toList().getFirst().getId()).isEqualTo(user2.getId());
    }

    @Test
    public void testDeleteFriend() {
        friendStorage.addFriend(user1.getId(), user2.getId());
        assertThat(friendStorage.getFriends(user1.getId()).stream().toList().getFirst().getId()).isEqualTo(user2.getId());

        friendStorage.deleteFriend(user1.getId(), user2.getId());
        assertThat(friendStorage.getFriends(user1.getId()).size()).isEqualTo(0);
    }

}
