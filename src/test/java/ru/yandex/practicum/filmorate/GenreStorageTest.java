package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.DbStorage.GenreDbStorage;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreStorageTest {
    private final GenreDbStorage genreDbStorage;

    @Test
    public void testGetGenre() {

        assertThat(genreDbStorage.getGenre(2).getName()).isEqualTo("Драма");

    }

    @Test
    public void testGetGenres() {

        assertThat(genreDbStorage.getGenres().size()).isEqualTo(6);

    }

}
