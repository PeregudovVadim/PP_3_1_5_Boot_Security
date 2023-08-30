package ru.kata.spring.boot_security.demo.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findRoleByRoleName(String roleName) {
        try {
            return entityManager
                    .createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class)
                    .setParameter("role", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Role findRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> findRoleByIds(Long[] ids) {
        return entityManager
                .createQuery("SELECT r FROM Role r WHERE r.id IN :ids", Role.class)
                .setParameter("ids", Arrays.asList(ids))
                .getResultList();
    }

    @Override
    public List<Role> findAllRoles() {
        return entityManager.createQuery("SELECT r from Role r", Role.class).getResultList();
    }

    @Override
    @Transactional
    public void createRole(String roleName) {
        entityManager.persist(new Role(roleName));
    }
}
