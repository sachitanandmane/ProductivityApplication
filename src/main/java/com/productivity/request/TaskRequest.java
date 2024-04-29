package com.productivity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.productivity.enums.TaskCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {
    @NotBlank
    private String taskName;

    @PastOrPresent(message = "start or end time of the task can not be in future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime startDateTime;

    @PastOrPresent(message = "start or end time of the task can not be in future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime endDateTime;

    @Enumerated(value = EnumType.STRING)
    private TaskCategoryEnum taskCategory;
}
