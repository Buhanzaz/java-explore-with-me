package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.ClientStatistics;
import ru.practicum.dto.ResponseStatisticDto;
import ru.practicum.dto.StatisticDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.variables.StaticVariables.FORMATTER;

@Validated
@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientStatistics service;

    @PostMapping("/hit")
    public StatisticDto saveInformationAboutEndpoint(@RequestBody @Valid StatisticDto dto) {
        return service.addStatsInfo(dto);
    }

    @ResponseBody
    @GetMapping("/stats")
    public List<ResponseStatisticDto> getInformationAboutEndpoint(
            @RequestParam @DateTimeFormat(pattern = FORMATTER) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = FORMATTER) LocalDateTime end,
            @RequestParam(defaultValue = "") String[] uris,
            @RequestParam(defaultValue = "false") boolean unique) {

        return service.statisticsOutput(start, end, uris, unique);
    }
}
