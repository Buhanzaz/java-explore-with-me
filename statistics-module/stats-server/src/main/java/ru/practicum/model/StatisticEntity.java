package ru.practicum.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statistics")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String app;

    String uri;

    @Column(name = "ipv4")
    String ip;

    @Column(name = "date_of_creation")
    LocalDateTime timestamp;
}