package ru.practicum.admin.users.mapper;

import org.mapstruct.Mapper;
import ru.practicum.admin.users.model.dtos.NewUserRequest;
import ru.practicum.admin.users.model.dtos.UserDto;
import ru.practicum.admin.users.model.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity entity);

    UserEntity toEntity(NewUserRequest userRequest);
}
