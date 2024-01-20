package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.service.ServerService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.variables.StaticVariables.FORMATTER;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ServerController {

    private final ServerService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHit saveInformationAboutEndpoint(@RequestBody EndpointHit dto) {
        return service.addStatsInfo(dto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getInformationAboutEndpoint(
            @RequestParam @DateTimeFormat(pattern = FORMATTER) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = FORMATTER) LocalDateTime end,
            @RequestParam(defaultValue = "") String[] uris,
            @RequestParam(defaultValue = "false") boolean unique) {

        return service.statisticsOutput(start, end, uris, unique);
    }
}
