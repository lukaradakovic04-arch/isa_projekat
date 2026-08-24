package com.example.isalibrary.controller;

import com.example.isalibrary.model.Author;
import com.example.isalibrary.repository.AuthorRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", repository.findAll());
        return "authors/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", repository.findById(id).orElseThrow());
        return "authors/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String save(@ModelAttribute Author author, RedirectAttributes redirectAttributes) {
        repository.save(author);
        redirectAttributes.addFlashAttribute("message", "Autor je sacuvan.");
        return "redirect:/authors";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Author author = repository.findById(id).orElseThrow();
        if (!author.getBooks().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Autor je povezan sa knjigom i ne moze se obrisati.");
        } else {
            repository.delete(author);
            redirectAttributes.addFlashAttribute("message", "Autor je obrisan.");
        }
        return "redirect:/authors";
    }
}
