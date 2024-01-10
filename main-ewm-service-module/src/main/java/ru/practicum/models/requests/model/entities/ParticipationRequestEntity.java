package ru.practicum.models.requests.model.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.Status;
import ru.practicum.models.events.model.entities.EventEntity;
import ru.practicum.models.users.entities.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participation_request", uniqueConstraints = {@UniqueConstraint(name = "UniqueEventAndRequester", columnNames = {"event_id", "requester_id"})})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(nullable = false)
    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    EventEntity event;

    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id", nullable = false)
    UserEntity requester;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Status status;
}
