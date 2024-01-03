package ru.practicum.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.model.StatisticEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ServerStatsRepositoryTest {

    @Autowired
    ServerStatsRepository repository;

    private static Iterable<StatisticEntity> statisticEntitiesWithUri;

    @BeforeAll
    static void setUp() {
        StatisticEntity one = StatisticEntity.builder()
                .app("ewm-main-service")
                .uri("/events")
                .ip("121.0.0.1")
                .timestamp(LocalDateTime.of(2023, 12, 23, 0, 0, 0))
                .build();

        StatisticEntity two = StatisticEntity.builder()
                .app("ewm-main-service")
                .uri("/events/5")
                .ip("121.0.0.1")
                .timestamp(LocalDateTime.of(2023, 12, 24, 0, 0, 0))
                .build();

        StatisticEntity three = StatisticEntity.builder()
                .app("ewm-main-service")
                .uri("/events")
                .ip("121.0.0.1")
                .timestamp(LocalDateTime.of(2022, 10, 7, 11, 11, 11))
                .build();

        StatisticEntity four = StatisticEntity.builder()
                .app("ewm-main-service")
                .uri("/events/5")
                .ip("192.163.0.1")
                .timestamp(LocalDateTime.of(2022, 10, 7, 11, 11, 11))
                .build();

        statisticEntitiesWithUri = List.of(one, two, three, four);
    }

    @Test
    void getStatisticForACertainTimeWithUri() {
        repository.saveAll(statisticEntitiesWithUri);

        ResponseStatisticDto responseStatisticDto = ResponseStatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events")
                .hits(2L).build();

        List<ResponseStatisticDto> responseStatisticDtos = repository.getStatisticForACertainTimeWithUri(
                new String[]{"/events"},
                LocalDateTime.of(2020, 9, 6, 11, 11, 11),
                LocalDateTime.of(2035, 9, 6, 11, 11, 11)
        );

        assertEquals(responseStatisticDto, responseStatisticDtos.get(0));
    }

    @Test
    void getStatisticForACertainTimeWithUniqueIpAndUri() {
        repository.saveAll(statisticEntitiesWithUri);

        ResponseStatisticDto responseStatisticDto = ResponseStatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events")
                .hits(1L).build();

        List<ResponseStatisticDto> responseStatisticDtos = repository.getStatisticForACertainTimeWithUniqueIpAndUri(
                new String[]{"/events"},
                LocalDateTime.of(2020, 9, 6, 11, 11, 11),
                LocalDateTime.of(2035, 9, 6, 11, 11, 11)
        );

        assertEquals(responseStatisticDto, responseStatisticDtos.get(0));
    }

    @Test
    void getStatisticForACertainTime() {
        repository.saveAll(statisticEntitiesWithUri);

        ResponseStatisticDto responseStatisticDto = ResponseStatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events")
                .hits(2L).build();

        ResponseStatisticDto secondResponseStatisticDto = ResponseStatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events/5")
                .hits(1L).build();


        List<ResponseStatisticDto> responseStatisticDtos = repository.getStatisticForACertainTime(
                LocalDateTime.of(2020, 9, 6, 11, 11, 11),
                LocalDateTime.of(2035, 9, 6, 11, 11, 11)
        );

        assertEquals(responseStatisticDto, responseStatisticDtos.get(0));
        assertEquals(secondResponseStatisticDto, responseStatisticDtos.get(1));
        assertEquals(secondResponseStatisticDto, responseStatisticDtos.get(2));
    }

    @Test
    void getStatisticForACertainTimeWithUniqueIp() {
        repository.saveAll(statisticEntitiesWithUri);

        ResponseStatisticDto responseStatisticDto = ResponseStatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events")
                .hits(1L).build();

        ResponseStatisticDto secondResponseStatisticDto = ResponseStatisticDto.builder()
                .app("ewm-main-service")
                .uri("/events/5")
                .hits(1L).build();

        List<ResponseStatisticDto> responseStatisticDtos = repository.getStatisticForACertainTimeWithUniqueIp(
                LocalDateTime.of(2020, 9, 6, 11, 11, 11),
                LocalDateTime.of(2035, 9, 6, 11, 11, 11)
        );

        assertEquals(responseStatisticDto, responseStatisticDtos.get(0));
        assertEquals(secondResponseStatisticDto, responseStatisticDtos.get(1));
    }
}