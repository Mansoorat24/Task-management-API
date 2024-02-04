package com.ApiWiz.Task.Management.Entities;

import com.ApiWiz.Task.Management.Enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
}