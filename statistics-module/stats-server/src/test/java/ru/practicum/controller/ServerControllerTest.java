package ru.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.service.ServerService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ServerControllerTest {

    @MockBean
    private final ServerService service;
    private final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    private static List<ViewStats> viewStatsTest;
    private static EndpointHit endpointHitTest;

    @BeforeAll
    static void setUp() {
        endpointHitTest = EndpointHit.builder()
                .app("ewm-main-service")
                .uri("/events")
                .ip("121.0.0.1")
                .timestamp(LocalDateTime.of(2023, 12, 23, 0, 0, 0))
                .build();

        viewStatsTest = List.of(ViewStats.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(6L)
                .build());
    }

    @Test
    @SneakyThrows
    void saveInformationAboutEndpoint() {
        when(service.addStatsInfo(any(EndpointHit.class))).thenReturn(endpointHitTest);

        String result = mockMvc.perform(post("/hit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitTest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        EndpointHit endpointHit = objectMapper.readValue(result, EndpointHit.class);

        assertEquals(endpointHit, endpointHitTest);
    }

    @Test
    @SneakyThrows
    void getInformationAboutEndpoint() {
        when(service.statisticsOutput(any(LocalDateTime.class), any(LocalDateTime.class),
                any(String[].class), anyBoolean()))
                .thenReturn(viewStatsTest);

        String result = mockMvc.perform(get("/stats")
                        .param("start", "2022-09-06 11:00:23")
                        .param("end", "2022-09-07 11:00:23")
                        .param("uris", "{\"/events/1\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String testEqualsDto = objectMapper.writeValueAsString(viewStatsTest);

        assertEquals(testEqualsDto, result);

    }
}