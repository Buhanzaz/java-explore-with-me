package ru.practicum.users.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.users.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.practicum.variables.StaticVariables.FORMATTER;

/**
 * DTO for {@link ru.practicum.users.model.entities.ParticipationRequestEntity}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto implements Serializable {
     Long id;
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
     LocalDateTime created;
     Long event;
     Long requester;
     Status status;
}