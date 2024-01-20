package ru.practicum.web.requests.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.State;
import ru.practicum.exceptons.excepton.NotFoundException;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.web.events.repository.EventRepository;
import ru.practicum.exceptons.excepton.ConflictException;
import ru.practicum.enums.Status;
import ru.practicum.mappers.requests.mapper.RequestMapper;
import ru.practicum.models.requests.model.dtos.ParticipationRequestDto;
import ru.practicum.models.requests.model.entities.ParticipationRequestEntity;
import ru.practicum.web.requests.repository.RequestRepository;
import ru.practicum.web.requests.service.PrivateUserRequestService;
import ru.practicum.models.users.model.entities.UserEntity;
import ru.practicum.web.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class PrivateUserRequestServiceImpl implements PrivateUserRequestService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;


    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getParticipationRequest(Long userId) {
        List<ParticipationRequestEntity> requestsEntities = requestRepository.findAllByRequester_Id(userId);
        return requestsEntities.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto participationRequest(Long userId, Long eventId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user"));
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Not found event"));

        if (eventEntity.getState().equals(State.PUBLISHED)) {
            if (!eventEntity.getInitiator().equals(userEntity)) {
                long confirmedRequests = eventEntity.getConfirmedRequests();
                long participantLimit = eventEntity.getParticipantLimit();


                if (confirmedRequests != participantLimit || participantLimit == 0) {
                    ParticipationRequestEntity.ParticipationRequestEntityBuilder requestEntity = ParticipationRequestEntity.builder();
                    requestEntity.created(LocalDateTime.now());
                    requestEntity.event(eventEntity);
                    requestEntity.requester(userEntity);

                    if (participantLimit != 0) {
                        if (eventEntity.getRequestModeration()) {
                            requestEntity.status(Status.PENDING);
                        } else {
                            requestEntity.status(Status.CONFIRMED);
                            eventEntity.setConfirmedRequests(eventEntity.getConfirmedRequests() + 1);
                        }
                    } else {
                        requestEntity.status(Status.CONFIRMED);
                        eventEntity.setConfirmedRequests(eventEntity.getConfirmedRequests() + 1);
                    }

                    return requestMapper.toDto(requestRepository.save(requestEntity.build()));
                } else {
                    throw new ConflictException("The limit of participants is filled");
                }
            } else {
                throw new ConflictException("You can't participate in the event you created");
            }
        } else {
            throw new ConflictException("This event is published or canceled");
        }
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        ParticipationRequestEntity requestEntity = requestRepository.findFirstByRequester_IdAndId(userId, requestId)
                .orElseThrow(() -> new NotFoundException("Not found participation request"));
        EventEntity eventEntity = eventRepository.findById(requestEntity.getEvent().getId())
                .orElseThrow(() -> new NotFoundException("Not found event"));

        if (requestEntity.getStatus().equals(Status.PENDING)) {
            requestEntity.setStatus(Status.CANCELED);
        } else if (requestEntity.getStatus().equals(Status.CONFIRMED)) {
            requestEntity.setStatus(Status.CANCELED);
            eventEntity.setConfirmedRequests(eventEntity.getConfirmedRequests() - 1);
        }

        return requestMapper.toDto(requestEntity);
    }
}
