package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHitEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServerStatsRepository extends JpaRepository<EndpointHitEntity, Long> {
    @Query("select new ru.practicum.dto.ViewStats (e.app, e.uri, count(e.ip)) from EndpointHitEntity e " +
            "where e.uri in ?1 and e.timestamp between ?2 and ?3 group by e.ip, e.uri, e.app order by count(e.ip) desc")
    List<ViewStats> getStatisticForACertainTimeWithUri(String[] uris, LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.ViewStats (e.app, e.uri, count(e.ip)) from EndpointHitEntity e " +
            "where e.timestamp between ?1 and ?2 group by e.ip, e.uri, e.app order by count(e.ip) desc")
    List<ViewStats> getStatisticForACertainTime(LocalDateTime start, LocalDateTime end);

    @Query("select distinct new ru.practicum.dto.ViewStats (e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHitEntity e where e.timestamp between ?1 and ?2 group by e.ip, e.uri, e.app order by count(distinct e.ip) desc")
    List<ViewStats> getStatisticForACertainTimeWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("select distinct new ru.practicum.dto.ViewStats (e.app, e.uri, count(distinct e.ip)) " +
            "from EndpointHitEntity e where e.uri in (?1) and e.timestamp between ?2 and ?3 group by e.ip, e.uri, e.app " +
            "order by count(distinct e.ip) desc")
    List<ViewStats> getStatisticForACertainTimeWithUniqueIpAndUri(String[] uris, LocalDateTime start, LocalDateTime end);
}
