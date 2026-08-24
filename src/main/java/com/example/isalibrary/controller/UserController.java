package com.example.isalibrary.controller;

import com.example.isalibrary.dto.UserForm;
import com.example.isalibrary.repository.RoleRepository;
import com.example.isalibrary.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("roles", roleRepository.findAll());
        return "users/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("userForm", userService.toForm(userService.findById(id)));
        model.addAttribute("roles", roleRepository.findAll());
        return "users/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("userForm") UserForm form,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "users/form";
        }
        try {
            userService.save(form);
            redirectAttributes.addFlashAttribute("message", "Korisnik je sacuvan.");
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", roleRepository.findAll());
            return "users/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Korisnik je obrisan.");
        return "redirect:/users";
    }
}
