package ru.practicum.admin.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.admin.users.mapper.UserMapper;
import ru.practicum.admin.users.model.dtos.NewUserRequest;
import ru.practicum.admin.users.model.dtos.UserDto;
import ru.practicum.admin.users.model.entities.UserEntity;
import ru.practicum.admin.users.repository.AdminUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class AdminServiceImpl implements AdminService {

    private final AdminUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUser(NewUserRequest userRequest) {
        UserEntity entity = userMapper.toEntity(userRequest);
        UserEntity save = userRepository.save(entity);

        return userMapper.toDto(save);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getInfoOfUser(List<Long> ids, Pageable pageForUsers) {
        List<UserEntity> byIds;

        if (!ids.isEmpty()) {
            byIds = userRepository.findByIds(ids, pageForUsers);
        } else {
            byIds = userRepository.findAll();
        }

        return byIds.stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
