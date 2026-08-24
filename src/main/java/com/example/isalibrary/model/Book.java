package com.example.isalibrary.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, unique = true, length = 30)
    private String isbn;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    public Book() {}

    public Book(String title, String isbn, Integer publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Set<Author> getAuthors() { return authors; }
    public void setAuthors(Set<Author> authors) { this.authors = authors; }
}
