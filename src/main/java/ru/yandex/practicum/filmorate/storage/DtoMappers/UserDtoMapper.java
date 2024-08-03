package ru.yandex.practicum.filmorate.storage.DtoMappers;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DtoModel.UserDto;

public class UserDtoMapper {
    public static UserDto mapToUserDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setBirthday(user.getBirthday());
        dto.setFriends(user.getFriends());
        return dto;
    }
}
