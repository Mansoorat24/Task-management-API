package com.ApiWiz.Task.Management.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.* ;
import java.util.ArrayList;
import java.util.List;
import com.ApiWiz.Task.Management.Enums.TaskStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
@Entity
@Table(name = "tasksList")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId ;
    @Column(name = "description")
    private String description ;
    @Column(name = "title" , nullable = false , columnDefinition = "VARCHAR(100)" )
    private String title ;
    @CreationTimestamp
    LocalDate created ;
    @Column(name = "due_date" , nullable = false)
    private LocalDate dueDate ;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus ;
    @JsonIgnore
    @ManyToMany(mappedBy ="tasks")
    private List<User> users = new ArrayList<>();


    @OneToMany
    private List<TaskDetails> assignedUsersAndStatus = new ArrayList<>();
}
