package ru.practicum.web.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.enums.State;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.ReviewEventFullDto;
import ru.practicum.models.events.model.dtos.UpdateEventAdminRequest;
import ru.practicum.models.events.model.dtos.UpdateGroupEventsAdminRequest;
import ru.practicum.web.events.service.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.utils.variables.StaticVariables.FORMATTER;

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
    public ReviewEventFullDto eventReviewByAdministrator(@PathVariable Long eventId,
                                                         @RequestBody @Valid UpdateEventAdminRequest updateEvent) {
        return service.reviewEvent(eventId, updateEvent);
    }

    @GetMapping("/waiting-review")
    public List<EventFullDto> eventsWaitingForAReview() {
        return service.eventsWaitingForAReview();
    }

    @PatchMapping("/reviews")
    public List<ReviewEventFullDto> reviewingEvents(
            @RequestBody @Valid UpdateGroupEventsAdminRequest groupEventsAdminRequest
    ) {
        return service.reviewingEvents(groupEventsAdminRequest);
    }

}
