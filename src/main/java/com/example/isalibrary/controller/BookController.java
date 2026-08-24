package com.example.isalibrary.controller;

import com.example.isalibrary.dto.BookForm;
import com.example.isalibrary.repository.AuthorRepository;
import com.example.isalibrary.repository.CategoryRepository;
import com.example.isalibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookService bookService,
                          CategoryRepository categoryRepository,
                          AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        addReferenceData(model);
        return "books/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("bookForm", bookService.toForm(bookService.findById(id)));
        addReferenceData(model);
        return "books/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("bookForm") BookForm form,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            addReferenceData(model);
            return "books/form";
        }
        try {
            bookService.save(form);
            redirectAttributes.addFlashAttribute("message", "Knjiga je sacuvana.");
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            addReferenceData(model);
            return "books/form";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Knjiga je obrisana.");
        return "redirect:/books";
    }

    private void addReferenceData(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
    }
}
