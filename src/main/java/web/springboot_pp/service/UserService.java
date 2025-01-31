package web.springboot_pp.service;

import web.springboot_pp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser(User user);
    void editUser(User user);
    void deleteUser(Long id);
    User getUserById(Long id);
}
