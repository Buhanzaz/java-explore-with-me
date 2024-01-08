package ru.practicum.events.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.models.entities.CategoryEntity;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.events.enums.State;
import ru.practicum.events.enums.StateActionForUsers;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.dtos.*;
import ru.practicum.events.model.entities.EventEntity;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.service.PrivateEventService;
import ru.practicum.exceptons.excepton.ChangeStatusRequestException;
import ru.practicum.exceptons.excepton.DataAndTimeException;
import ru.practicum.exceptons.excepton.UpdateEventException;
import ru.practicum.users.enums.Status;
import ru.practicum.users.mapper.RequestMapper;
import ru.practicum.users.model.dtos.EventRequestStatusUpdateRequest;
import ru.practicum.users.model.dtos.EventRequestStatusUpdateResult;
import ru.practicum.users.model.dtos.ParticipationRequestDto;
import ru.practicum.users.model.entities.ParticipationRequestEntity;
import ru.practicum.users.model.entities.UserEntity;
import ru.practicum.users.repository.RequestRepository;
import ru.practicum.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO Сделать нормальные ошибки
@Service
@Transactional
@RequiredArgsConstructor
class PrivateEventServiceImpl implements PrivateEventService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        EventEntity saveEntity;
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        CategoryEntity categoryEntity = categoryRepository.findById(newEventDto.getCategory()).orElseThrow();
        LocalDateTime time = newEventDto.getEventDate();

        if (time.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new DataAndTimeException("");
        } else {
            EventEntity eventEntity = eventMapper.toEntity(newEventDto);

            eventEntity.setCategory(categoryEntity);
            eventEntity.setInitiator(userEntity);

            saveEntity = eventRepository.save(eventEntity);
        }

        return eventMapper.toDto(saveEntity);
    }

    @Override
    public List<EventShortDto> getEventsForOwner(Long userId, Pageable pageable) {
        List<EventEntity> allByInitiatorId = eventRepository.findAllByInitiator_Id(userId, pageable);

        return allByInitiatorId.stream().map(eventMapper::toShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventForOwner(Long userId, Long eventId) {
        return eventMapper.toDto(eventRepository.findFirstByInitiator_IdAndId(userId, eventId).orElseThrow());
    }

    @Override
    @Transactional
    public EventFullDto ownerUpdateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventsDto) {
        EventEntity updatedEvent;
        EventEntity eventEntity = eventRepository.findFirstByInitiator_IdAndId(userId, eventId).orElseThrow();

        if (eventEntity.getState() != State.PUBLISHED) {
            if (updateEventsDto.getEventDate() != null) {
                LocalDateTime time = updateEventsDto.getEventDate();
                if (time.isBefore(LocalDateTime.now().plusHours(2))) {
                    throw new DataAndTimeException("");
                } else {
                    updatedEvent = update(updateEventsDto, eventEntity);
                }
            } else {
                updatedEvent = update(updateEventsDto, eventEntity);
            }
        } else {
            throw new UpdateEventException("");
        }
        return eventMapper.toDto(updatedEvent);
    }

    private EventEntity update(UpdateEventUserRequest updateEventsDto, EventEntity eventEntity) {
        EventEntity updatedEvent = eventMapper.updateForUser(updateEventsDto, eventEntity);
        if (updateEventsDto.getCategory() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(updateEventsDto.getCategory()).orElseThrow();
            updatedEvent.setCategory(categoryEntity);
        }

        if (updateEventsDto.getStateAction() == StateActionForUsers.SEND_TO_REVIEW) {
            updatedEvent.setState(State.PENDING);
        } else if (updateEventsDto.getStateAction() == StateActionForUsers.CANCEL_REVIEW) {
            updatedEvent.setState(State.CANCELED);
        }
        return updatedEvent;
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId, Long eventId) {
        List<ParticipationRequestEntity> requestEntities = requestRepository
                .findAllByEvent_Initiator_IdAndEvent_IdAndStatus(userId, eventId, Status.PENDING);
        return requestEntities.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult changingStatusRequest(Long userId, Long eventId,
                                                                EventRequestStatusUpdateRequest updateRequest) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        if (updateRequest == null) {
            return result;
        }

        EventEntity eventEntity = eventRepository.findFirstByInitiator_IdAndId(userId, eventId).orElseThrow();

        if (!eventEntity.getRequestModeration()) {
            return result;
        }

        List<ParticipationRequestEntity> participationRequestEntities = requestRepository.findAllByEvent_Initiator_IdAndEvent_IdAndStatus(userId, eventId, Status.PENDING);
        Long confirmedRequests = eventEntity.getConfirmedRequests();
        Long participantLimit = Long.valueOf(eventEntity.getParticipantLimit());
        List<Long> requestIds = updateRequest.getRequestIds();


        if (!confirmedRequests.equals(participantLimit) || participantLimit == 0) {
            List<ParticipationRequestEntity> confirmedRequestsList = new ArrayList<>();
            List<ParticipationRequestEntity> rejectedRequestsList = new ArrayList<>();
            for (ParticipationRequestEntity participationRequest : participationRequestEntities) {
                if (requestIds.contains(participationRequest.getId())) {
                    if (updateRequest.getStatus().equals(Status.CONFIRMED)) {
                        participationRequest.setStatus(Status.CONFIRMED);
                        confirmedRequestsList.add(participationRequest);
                        eventEntity.setConfirmedRequests(confirmedRequests++);
                    }

                    if (updateRequest.getStatus().equals(Status.REJECTED)) {
                        participationRequest.setStatus(Status.REJECTED);
                        rejectedRequestsList.add(participationRequest);
                    }

                } /*else {
                    participationRequest.setStatus(Status.REJECTED);
                    rejectedRequestsList.add(participationRequest);
                }*/
            }

            result.setConfirmedRequests(confirmedRequestsList.stream().map(requestMapper::toDto).collect(Collectors.toList()));
            result.setRejectedRequests(rejectedRequestsList.stream().map(requestMapper::toDto).collect(Collectors.toList()));
        } else {
            throw new ChangeStatusRequestException("The application limit has been reached");
        }
        return result;
    }
}