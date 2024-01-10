package ru.practicum.events.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.Sort;
import ru.practicum.enums.State;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.EventShortDto;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.service.PublicEventService;
import ru.practicum.exceptons.excepton.DataAndTimeException;
import ru.practicum.exceptons.excepton.NotFoundException;

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

@RequiredArgsConstructor
class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public EventFullDto getEvent(Long id) {
        EventEntity eventEntity = eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Not found event"));
        eventEntity.setViews(eventEntity.getViews() + 1);
        eventRepository.flush();
        return eventMapper.toDto(eventEntity);
    }

    @Override
    public List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Boolean onlyAvailable, Sort sort, Integer from, Integer size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventEntity> query = criteriaBuilder.createQuery(EventEntity.class);
        Root<EventEntity> root = query.from(EventEntity.class);
        Predicate criteria = criteriaBuilder.conjunction();

        if (text != null && !text.isBlank()) {
            Predicate annotation = criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")).as(String.class), "%" + text.toLowerCase() + "%");
            Predicate description = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")).as(String.class), "%" + text.toLowerCase() + "%");

            criteria = criteriaBuilder.and(criteria, criteriaBuilder.or(annotation, description));
        }

        if (categories != null && !categories.isEmpty()) {
            criteria = criteriaBuilder.and(criteria, root.get("category").in(categories));
        }

        if (paid != null) {
            criteria = criteriaBuilder.and(criteria, root.get("paid").in(paid));
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


        if (sort != null) {
            if (sort == Sort.EVENT_DATE) {
                query.orderBy(criteriaBuilder.asc(root.get("eventDate")));
            }
            if (sort.equals(Sort.VIEWS)) {
                query.orderBy(criteriaBuilder.asc(root.get("views")));
            }
        }

        if (onlyAvailable) {
            criteria = criteriaBuilder.and(criteria,
                    criteriaBuilder.notEqual(root.get("confirmedRequests"), root.get("participantLimit")));
        }

        criteria = criteriaBuilder.and(criteria, root.get("state").in(State.PUBLISHED));

        query.select(root).where(criteria).orderBy(criteriaBuilder.asc(root.get("eventDate")));
        List<EventEntity> eventEntityList = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();


        if (onlyAvailable) {
            eventEntityList = eventEntityList.stream()
                    .filter((event -> event.getConfirmedRequests() < (long) event.getParticipantLimit()))
                    .collect(Collectors.toList());
        }


        if (eventEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        return eventEntityList.stream().map(eventMapper::toShortDto).collect(Collectors.toList());
    }
}
