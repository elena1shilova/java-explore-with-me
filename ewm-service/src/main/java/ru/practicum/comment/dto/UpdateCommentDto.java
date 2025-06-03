package ru.practicum.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentDto {

    @Size(min = 1, max = 2000)
    private String text;

    private LocalDateTime updated;

}
