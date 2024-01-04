package ru.practicum.admin.users.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.users.model.dtos.NewUserRequest;
import ru.practicum.admin.users.model.dtos.UserDto;
import ru.practicum.admin.users.service.AdminService;
import ru.practicum.utils.Pages;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addNewUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        return service.addUser(newUserRequest);
    }

    @GetMapping()
    public List<UserDto> getInformationOfUsers(@RequestParam(required = false) List<Long> ids,
                                               @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "10", required = false) @Range(min = 1, max = 20) Integer size) {
        Pageable pageForUsers = Pages.getPageForUsers(from, size);
        return service.getInfoOfUser(ids, pageForUsers);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
    }
}
