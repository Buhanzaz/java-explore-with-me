package ru.practicum.models.events.model.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.models.categories.model.entities.CategoryEntity;
import ru.practicum.enums.State;
import ru.practicum.models.Locations.model.entities.LocationEntity;
import ru.practicum.models.users.model.entities.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
@Builder
@AllArgsConstructor
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
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    LocationEntity location;

    Boolean paid;

    Integer participantLimit;

    LocalDateTime publishedOn;

    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    State state;

    String title;

    Long views;
}
