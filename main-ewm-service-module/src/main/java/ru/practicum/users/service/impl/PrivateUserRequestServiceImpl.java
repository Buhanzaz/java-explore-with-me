package ru.practicum.users.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.model.entities.EventEntity;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exceptons.excepton.ParticipationRequestException;
import ru.practicum.users.enums.Status;
import ru.practicum.users.mapper.RequestMapper;
import ru.practicum.users.model.dtos.ParticipationRequestDto;
import ru.practicum.users.model.entities.ParticipationRequestEntity;
import ru.practicum.users.model.entities.UserEntity;
import ru.practicum.users.repository.RequestRepository;
import ru.practicum.users.repository.UserRepository;
import ru.practicum.users.service.PrivateUserRequestService;

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
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        EventEntity eventEntity = eventRepository.findById(eventId).orElseThrow();

        if (!eventEntity.getInitiator().equals(userEntity)) {
            Long confirmedRequests = eventEntity.getConfirmedRequests();
            Long participantLimit = Long.valueOf(eventEntity.getParticipantLimit());

            if (!confirmedRequests.equals(participantLimit) || participantLimit == 0) {
                ParticipationRequestEntity.ParticipationRequestEntityBuilder requestEntity = ParticipationRequestEntity.builder();
                requestEntity.created(LocalDateTime.now());
                requestEntity.event(eventEntity);
                requestEntity.requester(userEntity);

                if (eventEntity.getRequestModeration()) {
                    requestEntity.status(Status.PENDING);
                } else {
                    requestEntity.status(Status.CONFIRMED);
                    eventEntity.setConfirmedRequests(eventEntity.getConfirmedRequests() + 1);
                }

                return requestMapper.toDto(requestRepository.save(requestEntity.build()));
            } else {
                throw new ParticipationRequestException("");
            }
        } else {
            throw new ParticipationRequestException("");
        }
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        ParticipationRequestEntity requestEntity = requestRepository.findFirstByRequester_IdAndId(userId, requestId).orElseThrow();
        EventEntity eventEntity = eventRepository.findById(requestEntity.getEvent().getId()).orElseThrow();

        if (requestEntity.getStatus().equals(Status.PENDING)) {
            requestEntity.setStatus(Status.CANCELED);
        } else if (requestEntity.getStatus().equals(Status.CONFIRMED)) {
            requestEntity.setStatus(Status.CANCELED);
            eventEntity.setConfirmedRequests(eventEntity.getConfirmedRequests() - 1);
        }

        return requestMapper.toDto(requestEntity);
    }
}
