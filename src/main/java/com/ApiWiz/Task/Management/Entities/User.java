package com.ApiWiz.Task.Management.Entities;

import com.ApiWiz.Task.Management.Enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id" , nullable = false )
    private Long userId ;
    @Column(name = "username",nullable = false ,unique = true)
    private String username ;
    @Column(name = "password" , nullable = false )
    private String password ;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserType userType;
    @ManyToMany
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    public List<Task> tasks = new ArrayList<>();


   // public List<Task> tasks = new ArrayList<>();
}
