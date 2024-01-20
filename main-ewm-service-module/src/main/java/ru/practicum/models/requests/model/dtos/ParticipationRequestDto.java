package ru.practicum.models.requests.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.enums.Status;
import ru.practicum.models.requests.model.entities.ParticipationRequestEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.practicum.utils.variables.StaticVariables.FORMATTER;

/**
 * DTO for {@link ParticipationRequestEntity}
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