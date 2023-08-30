package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role findRoleByRoleName(String roleName);

    void createRole(String roleName);

    List<Role> getRoleByIds(Long[] ids);
}
