package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
    public void saveUser(User user, Long[] selectedRoles) {
        user.setRoles(new HashSet<>(roleService.getRoleByIds(selectedRoles)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.createUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, long id, Long[] selectedRoles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleService.getRoleByIds(selectedRoles)));
        userDAO.updateUser(user, id);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findUserByEmail(username);
    }

}
