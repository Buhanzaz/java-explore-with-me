package ru.practicum.models.events.model.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.models.categories.models.entities.CategoryEntity;
import ru.practicum.enums.State;
import ru.practicum.models.compilations.Locations.model.entities.LocationEntity;
import ru.practicum.models.users.entities.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "events")
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 2000)
    String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    CategoryEntity category;

    Long confirmedRequests;

    LocalDateTime createdOn;

    @Column(length = 7000)
    String description;

    LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id", nullable = false)
    UserEntity initiator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    LocationEntity location;

    Boolean paid;

    Integer participantLimit;

    LocalDateTime publishedOn;

    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    State state;

    String title;

    Long views;

    public EventEntity(Long id, String annotation, CategoryEntity category, Long confirmedRequests, LocalDateTime createdOn, String description, LocalDateTime eventDate, UserEntity initiator, LocationEntity location, Boolean paid, Integer participantLimit, LocalDateTime publishedOn, Boolean requestModeration, State state, String title, Long views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = Objects.requireNonNullElse(confirmedRequests, 0L);
        this.createdOn = Objects.requireNonNullElseGet(createdOn, LocalDateTime::now);
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = Objects.requireNonNullElse(paid, false);
        this.participantLimit = Objects.requireNonNullElse(participantLimit, 0);
        this.publishedOn = publishedOn;
        this.requestModeration = Objects.requireNonNullElse(requestModeration, true);
        this.title = title;
        this.views = Objects.requireNonNullElse(views, 0L);
        this.state = Objects.requireNonNullElse(state, State.PENDING);
    }
}
