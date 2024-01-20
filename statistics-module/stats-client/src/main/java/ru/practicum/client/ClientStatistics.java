package ru.practicum.client;

import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientStatistics {
    EndpointHit addStatsInfo(EndpointHit dto);

    List<ViewStats> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
