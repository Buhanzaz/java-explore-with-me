package ru.practicum.admin.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.practicum.admin.users.model.dtos.NewUserRequest;
import ru.practicum.admin.users.model.dtos.UserDto;
import ru.practicum.admin.users.service.AdminService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdminUserControllerTest {

    @MockBean
    private final AdminService service;
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

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
    @SneakyThrows
    void addNewUser() {
       when(service.addUser(any(NewUserRequest.class)))
               .thenReturn(firstResponseUser);

       String result = mockMvc.perform(post("/admin/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(mapper.writeValueAsString(firstUser)))
               .andExpect(status().is2xxSuccessful())
               .andReturn()
               .getResponse()
               .getContentAsString();

        UserDto userDto = mapper.readValue(result, UserDto.class);

        assertEquals(firstResponseUser, userDto);
    }

    @Test
    @SneakyThrows
    void getInformationOfUsers() {
        final List<Long> ids = List.of(1L);
        when(service.getInfoOfUser(anyList(), any(PageRequest.class)))
                .thenReturn(List.of(firstResponseUser));

        String result = mockMvc.perform(get("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("1", result);
    }

    @Test
    void deleteUser() {
    }
}