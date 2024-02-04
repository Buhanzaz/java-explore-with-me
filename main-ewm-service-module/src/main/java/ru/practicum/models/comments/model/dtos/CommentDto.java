package ru.practicum.models.comments.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.FieldName;
import ru.practicum.models.comments.model.entities.CommentEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link CommentEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {
    @NotNull
    private FieldName field;
    @NotNull
    @NotEmpty
    @NotBlank
    private String comment;
}