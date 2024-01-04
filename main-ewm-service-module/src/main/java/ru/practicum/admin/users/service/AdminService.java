package ru.practicum.admin.users.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.admin.users.model.dtos.NewUserRequest;
import ru.practicum.admin.users.model.dtos.UserDto;

import java.util.List;

public interface AdminService {
    UserDto addUser(NewUserRequest userRequest);

    void deleteUser(Long userId);

    List<UserDto> getInfoOfUser(List<Long> ids, Pageable pageForUsers);
}
