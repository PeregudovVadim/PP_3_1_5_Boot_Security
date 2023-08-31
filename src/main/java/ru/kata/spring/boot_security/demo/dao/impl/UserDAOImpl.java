package ru.kata.spring.boot_security.demo.dao.impl;


import org.hibernate.graph.GraphSemantic;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAllUser() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user, long id) {
        entityManager.merge(user);
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User deleteUser(long id) throws NullPointerException {
        User user = findById(id);
        if (null == user) {
            throw new NullPointerException("User not found");
        }
        entityManager.remove(user);
        entityManager.flush();
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return entityManager
                    .createQuery("SELECT u FROM User u WHERE u.email=:email", User.class)
                    .setParameter("email", email)
                    .setHint(GraphSemantic.FETCH.getJpaHintName(), entityManager.getEntityGraph("User.roles"))
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("User not found!");
        }
    }

}
