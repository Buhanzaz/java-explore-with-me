package ru.practicum.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.variables.StaticVariables.FORMATTER;

@Data
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHit {
    @NotBlank
    @NotNull
    String app;

    @NotBlank
    @NotNull
    String uri;

    @NotBlank
    @NotNull
    String ip;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMATTER)
    LocalDateTime timestamp;
}

