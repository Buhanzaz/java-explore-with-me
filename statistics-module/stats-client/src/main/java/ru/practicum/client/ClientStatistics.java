package ru.practicum.client;

import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.dto.StatisticDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientStatistics {
    StatisticDto addStatsInfo(StatisticDto dto);

    List<ResponseStatisticDto> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
