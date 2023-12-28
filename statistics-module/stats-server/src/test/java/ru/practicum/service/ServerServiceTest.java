package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.dto.StatisticDto;

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

    private static List<ResponseStatisticDto> responseStatisticDtoTest;
    private static StatisticDto statisticDtoTest;

    @BeforeAll
    static void setUp() {
        statisticDtoTest = StatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events")
                .ip("121.0.0.1")
                .timestamp(LocalDateTime.of(2023, 12, 23, 0, 0, 0))
                .build();

        responseStatisticDtoTest = List.of(ResponseStatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(6L)
                .build());

    }

    @Test
    void addStatsInfo() {
        when(service.addStatsInfo(any(StatisticDto.class))).thenReturn(statisticDtoTest);

        StatisticDto statisticDto = service.addStatsInfo(ServerServiceTest.statisticDtoTest);

        assertEquals(statisticDtoTest, statisticDto);
    }

    @Test
    void statisticsOutput() {
        when(service.statisticsOutput(any(LocalDateTime.class), any(LocalDateTime.class),
                any(String[].class), anyBoolean()))
                .thenReturn(responseStatisticDtoTest);

        List<ResponseStatisticDto> responseStatisticDtos = service.statisticsOutput(
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), new String[]{"test"}, true);

        assertEquals(responseStatisticDtoTest, responseStatisticDtos);
    }
}