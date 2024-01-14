package ru.practicum.web.events.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.models.categories.model.entities.CategoryEntity;
import ru.practicum.web.categories.repository.CategoryRepository;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionForUsers;
import ru.practicum.mappers.events.mapper.EventMapper;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.EventShortDto;
import ru.practicum.models.events.model.dtos.NewEventDto;
import ru.practicum.models.events.model.dtos.UpdateEventUserRequest;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.web.events.repository.EventRepository;
import ru.practicum.web.events.service.PrivateEventService;
import ru.practicum.exceptons.excepton.ConflictException;
import ru.practicum.exceptons.excepton.DataAndTimeException;
import ru.practicum.exceptons.excepton.EnumException;
import ru.practicum.exceptons.excepton.NotFoundException;
import ru.practicum.enums.Status;
import ru.practicum.models.requests.model.dtos.EventRequestStatusUpdateRequest;
import ru.practicum.models.requests.model.dtos.EventRequestStatusUpdateResult;
import ru.practicum.models.requests.model.dtos.ParticipationRequestDto;
import ru.practicum.models.requests.model.entities.ParticipationRequestEntity;
import ru.practicum.models.users.model.entities.UserEntity;
import ru.practicum.mappers.requests.mapper.RequestMapper;
import ru.practicum.web.requests.repository.RequestRepository;
import ru.practicum.web.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user"));
        CategoryEntity categoryEntity = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Not found category"));
        LocalDateTime time = newEventDto.getEventDate();

        if (time.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new DataAndTimeException("You can change the event no earlier than two hours before the start");
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
        return eventMapper.toDto(eventRepository.findFirstByInitiator_IdAndId(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Not found event")));
    }

    @Override
    @Transactional
    public EventFullDto ownerUpdateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventsDto) {
        EventEntity updatedEvent;
        EventEntity eventEntity = eventRepository.findFirstByInitiator_IdAndId(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Not found event"));

        if (eventEntity.getState() != State.PUBLISHED) {
            if (updateEventsDto.getEventDate() != null) {
                LocalDateTime time = updateEventsDto.getEventDate();
                if (time.isBefore(LocalDateTime.now().plusHours(2))) {
                    throw new DataAndTimeException("You can change the event no earlier than two hours before the start");
                } else {
                    updatedEvent = update(updateEventsDto, eventEntity);
                }
            } else {
                updatedEvent = update(updateEventsDto, eventEntity);
            }
        } else {
            throw new ConflictException("You don't have the rights to publish, send the event for review");
        }
        return eventMapper.toDto(updatedEvent);
    }

    private EventEntity update(UpdateEventUserRequest updateEventsDto, EventEntity eventEntity) {
        EventEntity updatedEvent = eventMapper.updateForUser(updateEventsDto, eventEntity);
        if (updateEventsDto.getCategory() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(updateEventsDto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Not found category"));
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
                .findAllByEvent_Initiator_IdAndEvent_Id(userId, eventId);
        return requestEntities.stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult changingStatusRequest(Long userId, Long eventId,
                                                                EventRequestStatusUpdateRequest updateRequest) {
        EventEntity eventEntity = eventRepository.findFirstByInitiator_IdAndId(userId, eventId).orElseThrow(
                () -> new NotFoundException("Not found event"));

        if (!eventEntity.getRequestModeration() && updateRequest == null) {
            return new EventRequestStatusUpdateResult();
        }

        List<Long> requestIds = updateRequest.getRequestIds();
        List<ParticipationRequestDto> participationRequestDtoList = requestMapper.toDtoList(requestRepository
                .findAllById(requestIds));

        if (participationRequestDtoList.isEmpty()) {
            throw new NotFoundException("Not found participation request");
        } else {
            long confirmedRequests = eventEntity.getConfirmedRequests();
            long participantLimit = eventEntity.getParticipantLimit();

            List<ParticipationRequestDto> confirmedRequestsList = new ArrayList<>();
            List<ParticipationRequestDto> rejectedRequestsList = new ArrayList<>();

            Status status = updateRequest.getStatus();

            for (ParticipationRequestDto participationRequest : participationRequestDtoList) {
                Status statusRequest = participationRequest.getStatus();

                if (confirmedRequests != participantLimit || participantLimit == 0) {
                    switch (status) {
                        case CONFIRMED:
                            if (statusRequest.equals(Status.PENDING)) {
                                participationRequest.setStatus(Status.CONFIRMED);
                                confirmedRequestsList.add(participationRequest);
                                confirmedRequests++;
                            } else {
                                continue;
                            }
                            break;
                        case REJECTED:
                            if (statusRequest.equals(Status.PENDING)) {
                                participationRequest.setStatus(Status.REJECTED);
                                rejectedRequestsList.add(participationRequest);
                            } else if (statusRequest.equals(Status.CONFIRMED)) {
                                throw new ConflictException("You cannot cancel a confirmed application");
                            } else {
                                continue;
                            }
                            break;
                        default:
                            throw new EnumException("You can confirm or rejected the status request");
                    }
                } else {
                    throw new ConflictException("The application limit has been reached");
                }
            }
            EventRequestStatusUpdateResult updateResult = new EventRequestStatusUpdateResult();
            updateResult.setConfirmedRequests(confirmedRequestsList);
            updateResult.setRejectedRequests(rejectedRequestsList);
            eventEntity.setConfirmedRequests(confirmedRequests);
            return updateResult;
        }
    }
}

