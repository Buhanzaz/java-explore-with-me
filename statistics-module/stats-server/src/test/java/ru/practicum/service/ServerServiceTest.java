package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.dto.ViewStats;
import ru.practicum.dto.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ServerServiceTest {

    @MockBean
    private final ServerService service;

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
    void addStatsInfo() {
        when(service.addStatsInfo(any(EndpointHit.class))).thenReturn(endpointHitTest);

        EndpointHit endpointHit = service.addStatsInfo(ServerServiceTest.endpointHitTest);

        assertEquals(endpointHitTest, endpointHit);
    }

    @Test
    void statisticsOutput() {
        when(service.statisticsOutput(any(LocalDateTime.class), any(LocalDateTime.class),
                any(String[].class), anyBoolean()))
                .thenReturn(viewStatsTest);

        List<ViewStats> viewStats = service.statisticsOutput(
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), new String[]{"test"}, true);

        assertEquals(viewStatsTest, viewStats);
    }
}