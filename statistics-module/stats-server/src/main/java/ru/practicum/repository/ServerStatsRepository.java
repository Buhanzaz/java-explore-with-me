package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.model.StatisticEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServerStatsRepository extends JpaRepository<StatisticEntity, Long> {
    @Query("select new ru.practicum.dto.ResponseStatisticDto (e.app, e.uri, count(e.ip)) from StatisticEntity e " +
            "where e.uri in ?1 and e.timestamp between ?2 and ?3 group by e.ip, e.uri, e.app order by count(e.ip) desc")
    List<ResponseStatisticDto> getStatisticForACertainTimeWithUri(String[] uris, LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.ResponseStatisticDto (e.app, e.uri, count(e.ip)) from StatisticEntity e " +
            "where e.timestamp between ?1 and ?2 group by e.ip, e.uri, e.app order by count(e.ip) desc")
    List<ResponseStatisticDto> getStatisticForACertainTime(LocalDateTime start, LocalDateTime end);

    @Query("select distinct new ru.practicum.dto.ResponseStatisticDto (e.app, e.uri, count(distinct e.ip)) " +
            "from StatisticEntity e where e.timestamp between ?1 and ?2 group by e.ip, e.uri, e.app order by count(distinct e.ip) desc")
    List<ResponseStatisticDto> getStatisticForACertainTimeWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("select distinct new ru.practicum.dto.ResponseStatisticDto (e.app, e.uri, count(distinct e.ip)) " +
            "from StatisticEntity e where e.uri in (?1) and e.timestamp between ?2 and ?3 group by e.ip, e.uri, e.app " +
            "order by count(distinct e.ip) desc")
    List<ResponseStatisticDto> getStatisticForACertainTimeWithUniqueIpAndUri(String[] uris, LocalDateTime start, LocalDateTime end);
}
