package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.web.WebBaseClient;
import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.dto.StatisticDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
class ClientStatisticsImplWeb extends WebBaseClient implements ClientStatistics {

    public ClientStatisticsImplWeb(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    @Override
    public StatisticDto addStatsInfo(StatisticDto dto) {
        return post(dto, new StatisticDto()).getBody();
    }

    @Override
    public List<ResponseStatisticDto> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        Map<String, Object> params = Map.of("start", start,
                "end", end,
                "uris", uris,
                "unique", unique);

        ResponseEntity<ArrayList<ResponseStatisticDto>> response = get(params, new ArrayList<>());

        return response.getBody();
    }
}
