package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.web.WebBaseClient;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import static ru.practicum.variables.StaticVariables.FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
class ClientStatisticsImplWeb extends WebBaseClient implements ClientStatistics {

    public ClientStatisticsImplWeb(@Value("${stats.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    @Override
    public EndpointHit addStatsInfo(EndpointHit dto) {
        return post(dto, new EndpointHit()).getBody();
    }

    @Override
    public List<ViewStats> statisticsOutput(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATTER);
        Map<String, Object> params = Map.of("start", start.format(formatter),
                "end", end.format(formatter),
                "uris", uris,
                "unique", unique);

        ResponseEntity<ArrayList<ViewStats>> response = get(params, new ArrayList<>());

        return response.getBody();
    }
}
