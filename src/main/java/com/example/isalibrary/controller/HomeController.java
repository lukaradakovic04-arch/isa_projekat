package com.example.isalibrary.controller;

import com.example.isalibrary.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public HomeController(BookRepository bookRepository,
                          AuthorRepository authorRepository,
                          CategoryRepository categoryRepository,
                          UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("bookCount", bookRepository.count());
        model.addAttribute("authorCount", authorRepository.count());
        model.addAttribute("categoryCount", categoryRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("roleCount", roleRepository.count());
        return "index";
    }
}
