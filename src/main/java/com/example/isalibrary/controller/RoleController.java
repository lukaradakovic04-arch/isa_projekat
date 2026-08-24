package com.example.isalibrary.controller;

import com.example.isalibrary.model.Role;
import com.example.isalibrary.repository.RoleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleRepository repository;

    public RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", repository.findAll());
        return "roles/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("role", new Role());
        return "roles/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("role", repository.findById(id).orElseThrow());
        return "roles/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Role role, RedirectAttributes redirectAttributes) {
        String normalized = role.getName().toUpperCase();
        if (!normalized.startsWith("ROLE_")) {
            normalized = "ROLE_" + normalized;
        }
        role.setName(normalized);
        repository.save(role);
        redirectAttributes.addFlashAttribute("message", "Rola je sacuvana.");
        return "redirect:/roles";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Role role = repository.findById(id).orElseThrow();
        if (!role.getUsers().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Rola je dodeljena korisniku i ne moze se obrisati.");
        } else {
            repository.delete(role);
            redirectAttributes.addFlashAttribute("message", "Rola je obrisana.");
        }
        return "redirect:/roles";
    }
}
