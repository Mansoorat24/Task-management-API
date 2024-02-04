package com.ApiWiz.Task.Management.Repository;

import com.ApiWiz.Task.Management.Entities.Task;
import com.ApiWiz.Task.Management.Enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByDueDateBefore(LocalDate date);
    List<Task> findByTaskStatus(TaskStatus status);
}
