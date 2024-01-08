package ru.practicum.users.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.practicum.users.model.dtos.ParticipationRequestDto;

import java.util.List;

@Data
public class EventRequestStatusUpdateResult {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;
}
