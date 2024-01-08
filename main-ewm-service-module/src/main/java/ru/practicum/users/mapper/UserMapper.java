package ru.practicum.users.mapper;

import org.mapstruct.*;
import ru.practicum.users.model.dtos.NewUserRequest;
import ru.practicum.users.model.dtos.UserDto;
import ru.practicum.users.model.entities.UserEntity;
import ru.practicum.users.model.dtos.UserShortDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(NewUserRequest userRequest);

    UserShortDto toShortDto(UserEntity userEntity);

    @Mapping(target = "email", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserShortDto userShortDto, @MappingTarget UserEntity userEntity);

    @Mapping(target = "email", ignore = true)
    UserEntity toEntity(UserShortDto userShortDto);
}
