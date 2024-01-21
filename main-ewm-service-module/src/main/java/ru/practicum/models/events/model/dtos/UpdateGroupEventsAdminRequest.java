package ru.practicum.models.events.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGroupEventsAdminRequest implements Serializable {
    private Map<Long, UpdateEventAdminRequest> reviewGroup;
}