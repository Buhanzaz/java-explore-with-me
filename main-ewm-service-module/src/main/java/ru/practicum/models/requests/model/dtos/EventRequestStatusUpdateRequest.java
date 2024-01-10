package ru.practicum.models.requests.model.dtos;

import lombok.Builder;
import lombok.Value;
import ru.practicum.enums.Status;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateRequest implements Serializable {
    List<Long> requestIds;

    @NotNull
    Status status;
}
