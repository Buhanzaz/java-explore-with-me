package ru.practicum.web.events.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionForAdmin;
import ru.practicum.exceptons.excepton.ConflictException;
import ru.practicum.exceptons.excepton.DataAndTimeException;
import ru.practicum.exceptons.excepton.NotFoundException;
import ru.practicum.mappers.comments.mapper.CommentMapper;
import ru.practicum.mappers.events.mapper.EventMapper;
import ru.practicum.models.categories.model.entities.CategoryEntity;
import ru.practicum.models.comments.model.entities.CommentEntity;
import ru.practicum.models.events.model.dtos.EventFullDto;
import ru.practicum.models.events.model.dtos.ReviewEventFullDto;
import ru.practicum.models.events.model.dtos.UpdateEventAdminRequest;
import ru.practicum.models.events.model.dtos.UpdateGroupEventsAdminRequest;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.web.categories.repository.CategoryRepository;
import ru.practicum.web.events.repository.CommentRepository;
import ru.practicum.web.events.repository.EventRepository;
import ru.practicum.web.events.service.AdminEventService;

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
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

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

        if (categories != null && !categories.isEmpty() && (!categories.contains(0L))) {
                criteria = criteriaBuilder.and(criteria, root.get("category").in(categories));

        }

        criteria = PublicEventServiceImpl.getPredicate(rangeStart, rangeEnd, criteriaBuilder, root, criteria);


        query.select(root).where(criteria);
        List<EventEntity> eventEntityList = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        if (eventEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        return eventEntityList.stream().map(eventMapper::toFullDto).collect(Collectors.toList());
    }

    @Override
    public ReviewEventFullDto reviewEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        EventEntity updatedEvent;
        EventEntity eventEntity = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Not found event"));
        LocalDateTime datePublication = LocalDateTime.now();
        LocalDateTime eventDate = eventEntity.getEventDate();

        if (eventEntity.getState().equals(State.PENDING)) {
            if (datePublication.isBefore(eventDate.minusHours(1))) {
                updatedEvent = eventMapper.updateForAdmin(updateEventAdminRequest, eventEntity);

                if (updateEventAdminRequest.getCategory() != null) {
                    CategoryEntity categoryEntity = categoryRepository.findById(updateEventAdminRequest.getCategory())
                            .orElseThrow(() -> new NotFoundException("Not found category"));
                    updatedEvent.setCategory(categoryEntity);
                }

                if (updateEventAdminRequest.getStateAction() == StateActionForAdmin.PUBLISH_EVENT) {
                    updatedEvent.setState(State.PUBLISHED);
                    updatedEvent.setPublishedOn(datePublication);
                } else if (updateEventAdminRequest.getStateAction() == StateActionForAdmin.REJECT_EVENT) {
                    List<CommentEntity> commentEntityList = commentMapper
                            .toEntityList(updateEventAdminRequest.getCommentsDtoList());

                    if (commentEntityList != null && !commentEntityList.isEmpty()) {
                        commentEntityList.forEach(commentEntity -> commentEntity.setEvent(eventEntity));
                        commentRepository.saveAll(commentEntityList);
                    }

                    updatedEvent.setState(State.CANCELED);

                    ReviewEventFullDto reviewDto = eventMapper.toReviewDto(updatedEvent);
                    reviewDto.setComments(updateEventAdminRequest.getCommentsDtoList());
                    return reviewDto;
                }
            } else {
                throw new DataAndTimeException("You can publish the event no earlier than an hour before the start");
            }
        } else {
            throw new ConflictException("This event is published or canceled");
        }

        return eventMapper.toReviewDto(updatedEvent);
    }

    @Override
    public List<EventFullDto> eventsWaitingForAReview() {
        return eventMapper.toDtoList(eventRepository.findAllByState(State.PENDING));
    }

    @Override
    public List<ReviewEventFullDto> reviewingEvents(UpdateGroupEventsAdminRequest reviewGroup) {
        if (reviewGroup.getReviewGroup().isEmpty()) {
            return Collections.emptyList();
        }

        return reviewGroup.getReviewGroup().keySet().stream()
                .map(id -> reviewEvent(id, reviewGroup.getReviewGroup().get(id)))
                .collect(Collectors.toList());
    }
}
