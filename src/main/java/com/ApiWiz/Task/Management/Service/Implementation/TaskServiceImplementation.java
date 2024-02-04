package com.ApiWiz.Task.Management.Service.Implementation;

import com.ApiWiz.Task.Management.Entities.Task;
import com.ApiWiz.Task.Management.Entities.TaskDetails;
import com.ApiWiz.Task.Management.Enums.TaskStatus;
import com.ApiWiz.Task.Management.Repository.TaskDetailsRepository;
import com.ApiWiz.Task.Management.Repository.TaskRepository;
import com.ApiWiz.Task.Management.Repository.UserRepository;
import com.ApiWiz.Task.Management.Service.TaskService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ApiWiz.Task.Management.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
@Service
public class TaskServiceImplementation implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImplementation.class);

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskDetailsRepository taskDetailsRepository;
    @Override
    public String createTask(Task task) {
        taskRepository.save(task);
        log.info("Task created successfully: {}", task);
        return task.toString();
    }
    @Override
    public String assignTask(Long taskId, List<Long> userIds) {
        Task task = getTaskById(taskId);

        if (task != null) {
           // List<User> assignedUsers = userRepository.findAllById(userIds);
            List<User> assignedUsers=userRepository.findAllById(userIds);
            List<TaskDetails> taskDetailsList = new ArrayList<>();

            for (User user : assignedUsers) {
                TaskDetails taskDetails = new TaskDetails();
                taskDetails.setUserId(user.getUserId());
                taskDetails.setTaskStatus(task.getTaskStatus());
                taskDetails.setDueDate(task.getDueDate());
                taskDetailsList.add(taskDetails);
                user.getTasks().add(task);
                task.getUsers().add(user);
                taskDetailsRepository.save(taskDetails);
            }

            task.getAssignedUsersAndStatus().addAll(taskDetailsList);

            taskRepository.save(task);
            log.info("Task assigned successfully. Task ID: {}, Assigned Users: {}", taskId, userIds);
            return "Task assigned successfully.";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found.");
        }
    }
    @Override
    public Task updateTask(Task task, Long taskId) {
        Task currtask = getTaskById(taskId);
        currtask.setTaskStatus(task.getTaskStatus());
        currtask.setDueDate(task.getDueDate());
        currtask.setTitle(task.getTitle());
        currtask.setDescription(task.getDescription());
        log.info("Task updated successfully. Task ID: {}", taskId);
        return currtask;
    }
    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found."));
    }
    @Override
    public String deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
        log.info("Task deleted successfully.Task ID: {}", taskId);
        return "successful";
    }
    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        log.info("Retrieved all tasks: {}", tasks);
        return tasks;
    }
    public Page<Task> getAllTasksPaginated(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page, size));
    }
    @Override
    public TaskDetails getTaskStatusOfUser(Long taskId, Long userId) {
        return getTaskDetails(taskId, userId);
    }
    public TaskDetails getTaskDetails(Long taskId, Long userId) {
        Task task = getTaskById(taskId);
        return task.getAssignedUsersAndStatus().stream()
                .filter(td -> td.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskDetailsRepository not found for user."));
    }
    @Override
    public List<TaskDetails> getAllTasksStatusOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        List<Task> task = user.getTasks();
        List<TaskDetails> taskDetails = new ArrayList<>();

        for (Task t :task) {
            Long taskId = t.getTaskId();
            taskDetails.add(getTaskDetails(taskId, userId));
        }
        log.info("Retrieved all tasks status for user {}. Task details: {}", userId, taskDetails);
        return taskDetails;
    }
    @Override
    public List<TaskDetails> getUsersStatusByTaskId(Long taskId) {
        Task task = getTaskById(taskId);
        log.info("Retrieved users status for task {}. Users details: {}", taskId, task.getAssignedUsersAndStatus());
        return task.getAssignedUsersAndStatus();
    }
    public List<Task> getTasksDueBefore(LocalDate date) {
        return taskRepository.findByDueDateBefore(date);
    }
    public List<Task> getTasksByTaskStatus(TaskStatus taskStatus) {
        return taskRepository.findByTaskStatus(taskStatus);
    }
}