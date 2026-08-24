package com.example.isalibrary.controller;

import com.example.isalibrary.model.Category;
import com.example.isalibrary.repository.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", repository.findAll());
        return "categories/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", repository.findById(id).orElseThrow());
        return "categories/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String save(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        repository.save(category);
        redirectAttributes.addFlashAttribute("message", "Kategorija je sacuvana.");
        return "redirect:/categories";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Category category = repository.findById(id).orElseThrow();
        if (!category.getBooks().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Kategorija je povezana sa knjigom i ne moze se obrisati.");
        } else {
            repository.delete(category);
            redirectAttributes.addFlashAttribute("message", "Kategorija je obrisana.");
        }
        return "redirect:/categories";
    }
}
