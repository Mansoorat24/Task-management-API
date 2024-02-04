package com.ApiWiz.Task.Management.Service;

import com.ApiWiz.Task.Management.Entities.*;

import java.util.List;

public interface UserService {
    User addUser(User user);
    String changePassword(Long userId , String oldPassword , String newPassword) ;
    String changeUsername(Long userId , String userName) ;
    String deleteUser(Long userId) ;

    User getUserTasksById(Long userId) ;
    List<User> getAllUsers();
}
