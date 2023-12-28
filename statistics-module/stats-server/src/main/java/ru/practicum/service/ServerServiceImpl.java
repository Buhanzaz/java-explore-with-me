package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.StatsServerMapper;
import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.dto.StatisticDto;
import ru.practicum.model.StatisticEntity;
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
    public StatisticDto addStatsInfo(StatisticDto dto) {
        StatisticEntity entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<ResponseStatisticDto> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        List<ResponseStatisticDto> views;
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
