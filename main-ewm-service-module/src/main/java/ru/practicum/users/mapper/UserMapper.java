package ru.practicum.users.mapper;

import org.mapstruct.*;
import ru.practicum.models.users.dtos.NewUserRequest;
import ru.practicum.models.users.dtos.UserDto;
import ru.practicum.models.users.entities.UserEntity;
import ru.practicum.models.users.dtos.UserShortDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(NewUserRequest userRequest);

    UserShortDto toShortDto(UserEntity userEntity);

    @Mapping(target = "email", ignore = true)
    UserEntity toEntity(UserShortDto userShortDto);
}
