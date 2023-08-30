package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.findAllRoles();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDAO.findRoleById(id);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleDAO.findRoleByRoleName(roleName);
    }

    @Override
    public void createRole(String roleName) {
        roleDAO.createRole(roleName);
    }

    @Override
    public List<Role> getRoleByIds(Long[] ids) {
        return roleDAO.findRoleByIds(ids);
    }

}
