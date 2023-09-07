package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUser() {
        return userDAO.findAllUser();
    }

    @Override
    public User getUserById(long id) {
        return userDAO.findById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.createUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getPassword() != null || !user.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDAO.updateUser(user, user.getId());
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDAO.deleteUser(id);
    }

}
