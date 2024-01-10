package ru.practicum.events.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.models.entities.CategoryEntity;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.events.enums.State;
import ru.practicum.events.enums.StateActionForAdmin;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.dtos.EventFullDto;
import ru.practicum.events.model.dtos.UpdateEventAdminRequest;
import ru.practicum.events.model.entities.EventEntity;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.service.AdminEventService;
import ru.practicum.exceptons.excepton.ConflictException;
import ru.practicum.exceptons.excepton.DataAndTimeException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final EntityManager entityManager;

    @Override
    public List<EventFullDto> searchEventForAdmin(List<Long> users, List<State> states,
                                                  List<Long> categories, LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd, Integer from, Integer size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventEntity> query = criteriaBuilder.createQuery(EventEntity.class);
        Root<EventEntity> root = query.from(EventEntity.class);
        Predicate criteria = criteriaBuilder.conjunction();

        if (users != null && !users.isEmpty()) {
            criteria = criteriaBuilder.and(criteria, root.get("initiator").in(users));
        }

        if (states != null) {
            criteria = criteriaBuilder.and(criteria, root.get("state").in(states));
        }

        if (categories != null && !categories.isEmpty()) {
            if (!categories.contains(0L)) {
                criteria = criteriaBuilder.and(criteria, root.get("category").in(categories));
            }
        }

        if (rangeStart != null && rangeEnd != null && !rangeStart.isBefore(rangeEnd)) {
            throw new DataAndTimeException("Initial time should be greater than the end time");
        }

        if (rangeStart != null) {
            Predicate greaterTime = criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), rangeStart);
            criteria = criteriaBuilder.and(criteria, greaterTime);
        }
        if (rangeEnd != null) {
            Predicate lessTime = criteriaBuilder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), rangeEnd);
            criteria = criteriaBuilder.and(criteria, lessTime);
        }


        query.select(root).where(criteria);
        List<EventEntity> eventEntityList = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        if (eventEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        return eventEntityList.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto reviewEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        EventEntity updatedEvent;
        EventEntity eventEntity = eventRepository.findById(eventId).orElseThrow();
        LocalDateTime datePublication = LocalDateTime.now();
//        if (!eventEntity.getState().equals(State.PUBLISHED) && !eventEntity.getState().equals(State.CANCELED)) {
        if (eventEntity.getState().equals(State.PENDING)) {
            if (datePublication.isBefore(eventEntity.getEventDate().minusHours(1))) {
                updatedEvent = eventMapper.updateForAdmin(updateEventAdminRequest, eventEntity);

                if (updateEventAdminRequest.getCategory() != null) {
                    CategoryEntity categoryEntity = categoryRepository.findById(updateEventAdminRequest.getCategory()).orElseThrow();
                    updatedEvent.setCategory(categoryEntity);
                }

                if (updateEventAdminRequest.getStateAction() == StateActionForAdmin.PUBLISH_EVENT) {
                    updatedEvent.setState(State.PUBLISHED);
                    updatedEvent.setPublishedOn(datePublication);
                } else if (updateEventAdminRequest.getStateAction() == StateActionForAdmin.REJECT_EVENT) {
                    updatedEvent.setState(State.CANCELED);
                }
//                else {
//                    throw new UpdateEventException("The event has already passed");
//                }
            } else {
                throw new DataAndTimeException("You can publish the event no earlier than an hour before the start");
            }
        } else {
            throw new ConflictException("This event is published or canceled");
        }

        return eventMapper.toDto(updatedEvent);
    }
}
