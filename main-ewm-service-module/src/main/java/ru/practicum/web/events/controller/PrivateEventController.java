package ru.practicum.web.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.web.events.service.PrivateEventService;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.EventShortDto;
import ru.practicum.models.events.model.dtos.NewEventDto;
import ru.practicum.models.events.model.dtos.UpdateEventUserRequest;
import ru.practicum.models.requests.model.dtos.EventRequestStatusUpdateRequest;
import ru.practicum.models.requests.model.dtos.EventRequestStatusUpdateResult;
import ru.practicum.models.requests.model.dtos.ParticipationRequestDto;
import ru.practicum.utils.Pages;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService service;

    @GetMapping("/events")
    public List<EventShortDto> getEventsForOwner(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "10", required = false) @Min(1) Integer size) {
        Pageable pageable = Pages.getPageForEvents(from, size);
        return service.getEventsForOwner(userId, pageable);
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addNewEvent(@PathVariable Long userId, @RequestBody @Valid NewEventDto newEventDto) {
        return service.addEvent(userId, newEventDto);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEventForOwner(@PathVariable Long userId, @PathVariable Long eventId) {
        return service.getEventForOwner(userId, eventId);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto ownerUpdateEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                         @RequestBody @Valid UpdateEventUserRequest updateEventsDto) {
        return service.ownerUpdateEvent(userId, eventId, updateEventsDto);
    }

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> gettingAllUserRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return service.getUserRequests(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changingStatusRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                                                @RequestBody @Valid EventRequestStatusUpdateRequest updateRequest) {
        return service.changingStatusRequest(userId, eventId, updateRequest);
    }
}
