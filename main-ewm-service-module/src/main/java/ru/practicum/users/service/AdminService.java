package ru.practicum.users.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.users.dtos.NewUserRequest;
import ru.practicum.models.users.dtos.UserDto;

import java.util.List;

public interface AdminService {
    UserDto addUser(NewUserRequest userRequest);

    void deleteUser(Long userId);

    List<UserDto> getInfoOfUser(List<Long> ids, Pageable pageForUsers);

    UserDto getUser(Long userId);
}
