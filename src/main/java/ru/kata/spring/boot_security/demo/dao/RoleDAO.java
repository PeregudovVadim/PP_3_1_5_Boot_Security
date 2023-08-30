package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDAO {
    Role findRoleByRoleName(String roleName);

    Role findRoleById(Long id);

    List<Role> findRoleByIds(Long[] ids);

    List<Role> findAllRoles();

    void createRole(String role);
}
