package ru.practicum.requests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.requests.model.dtos.ParticipationRequestDto;
import ru.practicum.requests.service.PrivateUserRequestService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateUserRequestController {
    private final PrivateUserRequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getParticipationRequest(@PathVariable Long userId) {
        return service.getParticipationRequest(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
        public ParticipationRequestDto participationRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        return service.participationRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return service.cancelParticipationRequest(userId, requestId);
    }
}
