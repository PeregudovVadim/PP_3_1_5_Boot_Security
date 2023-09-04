package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/")
    public String mainPageAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("userEmail", authentication.getName());
        model.addAttribute("userRoles", roles);

        model.addAttribute("usersList", userService.getAllUser());
        model.addAttribute("newUser", new User());
        model.addAttribute("listRoles",roleService.getAllRoles());
        model.addAttribute("userAuth", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "home-panel";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("selectedRoles") Long[] selectedRoles) {
        userService.saveUser(user, selectedRoles);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id,
                             @RequestParam("selectedRoles") Long[] selectedRoles) {
        userService.updateUser(user, id, selectedRoles);
        return "redirect:/admin/";
    }
}
