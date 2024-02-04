package ru.practicum.models.comments.model.entities;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.enums.FieldName;
import ru.practicum.models.events.model.entities.EventEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @Enumerated(EnumType.STRING)
    private FieldName field;
    private String comment;
}
