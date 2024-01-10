package ru.practicum.users.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.model.dtos.NewUserRequest;
import ru.practicum.users.model.dtos.UserDto;
import ru.practicum.users.model.entities.UserEntity;
import ru.practicum.users.repository.UserRepository;
import ru.practicum.users.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
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

        if (ids != null && !ids.isEmpty()) {
            byIds = userRepository.findByIds(ids, pageForUsers);
        } else {
            byIds = userRepository.findAll(pageForUsers).toList();
        }

        return byIds.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long userId) {
        return userMapper.toDto(userRepository.findById(userId).orElseThrow());
    }
}
