package com.ApiWiz.Task.Management.Service;

import com.ApiWiz.Task.Management.Entities.Task;
import com.ApiWiz.Task.Management.Entities.TaskDetails;

import java.util.List;

public interface TaskService {
    String createTask(Task task) ;
    String assignTask(Long taskId , List<Long> userId);
    Task updateTask(Task task , Long taskId) ;
    Task getTaskById(Long taskId);
    String deleteTask(Long taskId);
    List<Task> getAllTasks();
    TaskDetails getTaskStatusOfUser(Long taskId, Long userId);
    List<TaskDetails> getAllTasksStatusOfUser(Long userId);

    List<TaskDetails> getUsersStatusByTaskId(Long taskId);
}