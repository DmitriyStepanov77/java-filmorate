package ru.yandex.practicum.filmorate.storage.DbStorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;
import java.util.Optional;

@Repository
public class MpaDbStorage extends BaseDb<MPA> {
    private static final String SELECT_MPA = "SELECT * FROM MPA WHERE MPA_ID = ?";
    private static final String SELECT_MPAS = "SELECT * FROM MPA";

    public MpaDbStorage(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    public MPA getMPA(long id) {
        Optional<MPA> mpa = findOne(SELECT_MPA, id);
        if (mpa.isPresent())
            return mpa.get();
        else
            throw new NotFoundException("Рейтинг с данным ID не найден");
    }

    public Collection<MPA> getMPAs() {
        return findMany(SELECT_MPAS);
    }
}
