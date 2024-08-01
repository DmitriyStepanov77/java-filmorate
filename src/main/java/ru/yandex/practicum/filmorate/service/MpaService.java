package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.DbStorage.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.DtoMappers.MpaDtoMapper;
import ru.yandex.practicum.filmorate.storage.DtoModel.MpaDto;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaDbStorage mpaDbStorage;

    public Collection<MpaDto> getMpas() {
        return mpaDbStorage.getMPAs().stream()
                .map(MpaDtoMapper::mapToMpaDTO)
                .sorted(Comparator.comparingInt(MpaDto::getId))
                .collect(Collectors.toList());
    }

    public MpaDto getMpa(int id) {
        return MpaDtoMapper.mapToMpaDTO(mpaDbStorage.getMPA(id));
    }

}
