package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.dto.StatisticDto;

import java.time.LocalDateTime;
import java.util.List;


public interface ServerService {
    @Transactional
    StatisticDto addStatsInfo(StatisticDto dto);

    @Transactional(readOnly = true)
    List<ResponseStatisticDto> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
