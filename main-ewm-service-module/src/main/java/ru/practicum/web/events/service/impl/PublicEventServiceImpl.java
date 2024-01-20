package ru.practicum.web.events.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.ClientStatistics;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.enums.Sort;
import ru.practicum.enums.State;
import ru.practicum.exceptons.excepton.ClientStatisticsException;
import ru.practicum.exceptons.excepton.DataAndTimeException;
import ru.practicum.exceptons.excepton.NotFoundException;
import ru.practicum.mappers.events.mapper.EventMapper;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.EventShortDto;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.web.events.repository.EventRepository;
import ru.practicum.web.events.service.PublicEventService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.utils.variables.StaticVariables.NAME_SERVICE;

@Service

@RequiredArgsConstructor
class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EntityManager entityManager;
    private final ClientStatistics clientStatistics;

    @Override
    @Transactional
    public EventFullDto getEvent(Long id, HttpServletRequest request) {
        EventEntity eventEntity = eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Not found event"));

        statisticsAndViews(eventEntity, request);
        return eventMapper.toDto(eventEntity);
    }

    @Override
    public List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Boolean onlyAvailable, Sort sort, Integer from, Integer size, HttpServletRequest request) {

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

        criteria = getPredicate(rangeStart, rangeEnd, criteriaBuilder, root, criteria);


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


        if (eventEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        statisticsAndViews(null, request);

        return eventEntityList.stream().map(eventMapper::toShortDto).collect(Collectors.toList());
    }

    static Predicate getPredicate(LocalDateTime rangeStart, LocalDateTime rangeEnd, CriteriaBuilder criteriaBuilder, Root<EventEntity> root, Predicate criteria) {
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
        return criteria;
    }

    private void statisticsAndViews(@Nullable EventEntity eventEntity,
                                    HttpServletRequest request) {
        EndpointHit responceEndpointHit = null;

        try {
            EndpointHit endpointHit = EndpointHit.builder()
                    .app(NAME_SERVICE)
                    .uri(request.getRequestURI())
                    .ip(request.getRemoteAddr())
                    .timestamp(LocalDateTime.now())
                    .build();

            responceEndpointHit = clientStatistics.addStatsInfo(endpointHit);

        } catch (RuntimeException e) {
            //TODO Посылать ошибку в другое место но ни как в ErrorHandler так как
            throw new ClientStatisticsException("The statistics server is not responding");
        } finally {
            if (eventEntity != null && responceEndpointHit == null) {
                eventEntity.setViews(eventEntity.getViews() + 1);
            }
        }

        if (eventEntity != null) {
            LocalDateTime startTime = eventEntity.getPublishedOn();
            LocalDateTime endTime = eventEntity.getEventDate();

            String[] uris = {request.getRequestURI()};

            List<ViewStats> viewStats = clientStatistics.statisticsOutput(startTime, endTime, uris, true);

            if (eventEntity.getViews() < viewStats.size()) {
                eventEntity.setViews((long) viewStats.size());
            }
        }
    }
}
