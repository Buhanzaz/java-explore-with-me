package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;


public interface ServerService {
    @Transactional
    EndpointHit addStatsInfo(EndpointHit dto);

    @Transactional(readOnly = true)
    List<ViewStats> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
