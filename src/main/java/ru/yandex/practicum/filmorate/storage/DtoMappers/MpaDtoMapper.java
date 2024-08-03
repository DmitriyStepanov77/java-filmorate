package ru.yandex.practicum.filmorate.storage.DtoMappers;

import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.DtoModel.MpaDto;

public class MpaDtoMapper {
    public static MpaDto mapToMpaDTO(MPA mpa) {
        MpaDto dto = new MpaDto();
        dto.setId(mpa.getId());
        dto.setName(mpa.getName());
        return dto;
    }
}
