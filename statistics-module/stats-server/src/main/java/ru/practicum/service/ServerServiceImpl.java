package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.exceptons.excepton.DataAndTimeException;
import ru.practicum.mapper.StatsServerMapper;
import ru.practicum.model.EndpointHitEntity;
import ru.practicum.repository.ServerStatsRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServerServiceImpl implements ServerService {
    StatsServerMapper mapper;
    ServerStatsRepository repository;

    @Override
    public EndpointHit addStatsInfo(EndpointHit dto) {
        EndpointHitEntity entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<ViewStats> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        List<ViewStats> views;

        if (start.isAfter(end) || start.equals(end)) {
            throw new DataAndTimeException("The date was entered incorrectly");
        }

        if (unique) {
            if (uris.length != 0) {
                views = repository.getStatisticForACertainTimeWithUniqueIpAndUri(uris, start, end);
            } else {
                views = repository.getStatisticForACertainTimeWithUniqueIp(start, end);
            }
        } else {
            if (uris.length != 0) {
                views = repository.getStatisticForACertainTimeWithUri(uris, start, end);
            } else {
                views = repository.getStatisticForACertainTime(start, end);
            }
        }
        return views;
    }
}
