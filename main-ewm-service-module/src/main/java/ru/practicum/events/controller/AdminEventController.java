package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.enums.State;
import ru.practicum.events.model.dtos.EventFullDto;
import ru.practicum.events.model.dtos.UpdateEventAdminRequest;
import ru.practicum.events.service.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.variables.StaticVariables.FORMATTER;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    private final AdminEventService service;

    @GetMapping
    public List<EventFullDto> searchEventsForAdmin(@RequestParam(required = false) List<Long> users,
                                                   @RequestParam(required = false) List<State> states,
                                                   @RequestParam(required = false) List<Long> categories,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = FORMATTER) LocalDateTime rangeStart,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = FORMATTER) LocalDateTime rangeEnd,
                                                   @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                                   @RequestParam(defaultValue = "10", required = false) @Min(1) Integer size) {
        return service.searchEventForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("{eventId}")
    public EventFullDto EventReviewByAdministrator(@PathVariable Long eventId,
                                                   @RequestBody @Valid UpdateEventAdminRequest updateEvent) {
        return service.reviewEvent(eventId, updateEvent);
    }

}
