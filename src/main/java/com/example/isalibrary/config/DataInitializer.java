package com.example.isalibrary.config;

import com.example.isalibrary.model.*;
import com.example.isalibrary.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           AuthorRepository authorRepository,
                           BookRepository bookRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser("admin", passwordEncoder.encode("admin123"), true);
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            AppUser user = new AppUser("user", passwordEncoder.encode("user123"), true);
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }

        if (categoryRepository.count() == 0 && authorRepository.count() == 0 && bookRepository.count() == 0) {
            Category fiction = categoryRepository.save(new Category("Roman"));
            Category programming = categoryRepository.save(new Category("Programiranje"));

            Author orwell = authorRepository.save(new Author("George", "Orwell"));
            Author bloch = authorRepository.save(new Author("Joshua", "Bloch"));

            Book b1 = new Book("1984", "9780451524935", 1949);
            b1.setCategory(fiction);
            b1.setAuthors(Set.of(orwell));
            bookRepository.save(b1);

            Book b2 = new Book("Effective Java", "9780134685991", 2018);
            b2.setCategory(programming);
            b2.setAuthors(Set.of(bloch));
            bookRepository.save(b2);
        }
    }
}
