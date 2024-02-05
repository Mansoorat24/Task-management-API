package com.ApiWiz.Task.Management.Service.Implementation;

import com.ApiWiz.Task.Management.Entities.Task;
import com.ApiWiz.Task.Management.Repository.TaskRepository;
import com.ApiWiz.Task.Management.Repository.UserRepository;
import com.ApiWiz.Task.Management.Service.UserService;

import jakarta.persistence.EntityNotFoundException;
import com.ApiWiz.Task.Management.Entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImplementation.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public User addUser(User user) {
        userRepository.save(user);
        log.info("User added successfully:{}", user);
        return user;
    }

    @Override
    public String changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userRepository.save(user); // Save the updated user entity to the database
                log.info("Password changed successfully for user: {}", user);
                return "Password changed successfully";
            } else {
                log.warn("Wrong password for user: {}", user);
                return "Wrong password";
            }
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
    @Override
    public String changeUsername(Long userId, String newUsername) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(newUsername); // Update username in the user object
            userRepository.save(user); // Save the updated user entity to the database
            log.info("Username changed successfully for user: {}", user);
            return "Username changed successfully";
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
    @Override
    public String deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
            log.info("User deleted successfully. User ID: {}", userId);
            return "User deleted successfully";
        }
        throw new EntityNotFoundException("User not found ");
    }
    @Override
    public User getUserTasksById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Retrieved all users: {}", users);
        return users;
    }
    public Page<Task> getAllTasksPaginated(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page, size));
    }
}
