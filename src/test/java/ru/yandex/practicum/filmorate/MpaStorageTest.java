package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.storage.DbStorage.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.RowMappers.MpaRowMapper;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaDbStorage.class, MpaRowMapper.class})
public class MpaStorageTest {
    private final MpaDbStorage mpaDbStorage;

    @Test
    public void testGetMpa() {

        assertThat(mpaDbStorage.getMPA(2).getName()).isEqualTo("PG");

    }

    @Test
    public void testGetMpas() {

        assertThat(mpaDbStorage.getMPAs().size()).isEqualTo(5);

    }

}
