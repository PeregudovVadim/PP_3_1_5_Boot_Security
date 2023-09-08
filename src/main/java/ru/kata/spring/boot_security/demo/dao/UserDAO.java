package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> findAllUser();

    void createUser(User user);

    void updateUser(User user, long id);

    User findById(long id);

    User deleteUser(long id) throws NullPointerException;

    User findUserByEmail(String email);

}
