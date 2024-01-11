package ru.practicum.web.users.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.users.model.dtos.NewUserRequest;
import ru.practicum.models.users.model.dtos.UserDto;

import java.util.List;

public interface AdminService {
    UserDto addUser(NewUserRequest userRequest);

    void deleteUser(Long userId);

    List<UserDto> getInfoOfUser(List<Long> ids, Pageable pageForUsers);

    UserDto getUser(Long userId);
}
