package com.productivity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.productivity.enums.TaskCategoryEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskId;

    @Column(nullable = false)
    private String taskName;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_fk")
    @JsonIgnoreProperties("tasks")
    private Users user;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskCategoryEnum taskCategory;

}
