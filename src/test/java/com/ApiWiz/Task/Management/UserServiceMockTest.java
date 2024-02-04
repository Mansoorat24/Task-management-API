package com.ApiWiz.Task.Management;


import com.ApiWiz.Task.Management.Entities.User;
import com.ApiWiz.Task.Management.Repository.UserRepository;
import com.ApiWiz.Task.Management.Service.Implementation.UserServiceImplementation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceMockTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserServiceImplementation userService;

    @Test
    void addUser() {
        User user = new User();
        when(userRepo.save(any())).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
    }

    @Test
    void changePassword() {
        Long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = new User();
        user.setPassword(oldPassword);
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.changePassword(userId, oldPassword, newPassword);

        assertEquals("Password changed successfully", result);
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void changePasswordWrongPassword() {
        Long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = new User();
        user.setPassword("wrongPassword");
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.changePassword(userId, oldPassword, newPassword);

        assertEquals("Wrong password", result);
    }

    @Test
    void changePasswordUserNotFound() {
        Long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.changePassword(userId, oldPassword, newPassword));
    }

    @Test
    void changeUsername() {
        Long userId = 1L;
        String newUsername = "newUsername";
        User user = new User();
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.changeUsername(userId, newUsername);

        assertEquals("Username changed successfully", result);
        assertEquals(newUsername, user.getUsername());
    }

    @Test
    void changeUsernameUserNotFound() {
        Long userId = 1L;
        String newUsername = "newUsername";
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.changeUsername(userId, newUsername));
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        User user = new User();
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(userId);

        assertEquals("User deleted successfully", result);
    }

    @Test
    void deleteUserUserNotFound() {
        Long userId = 1L;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));
    }

    @Test
    void getUserTasksById() {
        Long userId = 1L;
        User user = new User();
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserTasksById(userId);

        assertNotNull(result);
    }

    @Test
    void getUserTasksByIdUserNotFound() {
        Long userId = 1L;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserTasksById(userId));
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepo.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(users.size(), result.size());
    }
}