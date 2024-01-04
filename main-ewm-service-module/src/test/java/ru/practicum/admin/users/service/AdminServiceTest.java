package ru.practicum.admin.users.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin.users.model.dtos.NewUserRequest;
import ru.practicum.admin.users.model.dtos.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Sql(scripts = {"file:src/main/resources/schema.sql"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestMethodOrder(MethodOrderer.MethodName.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminServiceTest {
    private final AdminServiceImpl service;
    private NewUserRequest firstUser;
    private UserDto firstResponseUser;
    private PageRequest page;

    @BeforeEach
    void setUp() {
        firstUser = NewUserRequest.builder()
                .name("Eugene")
                .email("test@mail.com")
                .build();

        firstResponseUser = UserDto.builder()
                .id(1L)
                .name("Eugene")
                .email("test@mail.com")
                .build();

        page = PageRequest.of(0 / 10, 10, Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    @Order(1)
    void addUser() {
        UserDto userDto = service.addUser(firstUser);

        assertEquals(userDto, firstResponseUser);
    }

    @Test
    @Order(2)
    void addUser_Error400() {
        final String errorMessage = "could not execute statement; SQL [n/a]; constraint [null]; " +
                "nested exception is org.hibernate.exception.ConstraintViolationException: " +
                "could not execute statement";

        service.addUser(firstUser);

        DataIntegrityViolationException conflictException = assertThrows(DataIntegrityViolationException.class,
                () -> service.addUser(firstUser)
        );

        assertEquals(errorMessage, conflictException.getMessage());
    }

    @Test
    @Order(3)
    void getInfoOfUser() {
        UserDto userDto = service.addUser(firstUser);
        List<UserDto> user = service.getInfoOfUser(List.of(userDto.getId()), page);

        assertEquals(firstResponseUser, user.get(0));
    }

    @Test
    @Order(4)
    void deleteUser() {
        UserDto userDto = service.addUser(firstUser);

        service.deleteUser(userDto.getId());

        List<UserDto> user = service.getInfoOfUser(List.of(userDto.getId()), page);

        assertTrue(user.isEmpty());
    }

    @Test
    @Order(5)
    void deleteUser_error404() {
        final String errorMessage = "No class ru.practicum.admin.users.model.entities.UserEntity " +
                "entity with id 100 exists!";

        EmptyResultDataAccessException emptyResultDataAccessException =
                assertThrows(EmptyResultDataAccessException.class, () -> service.deleteUser(100L));

        assertEquals(emptyResultDataAccessException.getMessage(), errorMessage);
    }
}