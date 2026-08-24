package com.example.isalibrary.service;

import com.example.isalibrary.dto.BookApiRequest;
import com.example.isalibrary.dto.BookApiResponse;
import com.example.isalibrary.dto.BookForm;
import com.example.isalibrary.model.Author;
import com.example.isalibrary.model.Book;
import com.example.isalibrary.model.Category;
import com.example.isalibrary.repository.AuthorRepository;
import com.example.isalibrary.repository.BookRepository;
import com.example.isalibrary.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       CategoryRepository categoryRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Knjiga nije pronadjena."));
    }

    public Book save(BookForm form) {
        Book book = form.getId() == null ? new Book() : findById(form.getId());
        applyForm(book, form.getTitle(), form.getIsbn(), form.getPublicationYear(),
                form.getCategoryId(), form.getAuthorIds());
        return bookRepository.save(book);
    }

    public Book saveApi(Long id, BookApiRequest request) {
        Book book = id == null ? new Book() : findById(id);
        applyForm(book, request.getTitle(), request.getIsbn(), request.getPublicationYear(),
                request.getCategoryId(), request.getAuthorIds());
        return bookRepository.save(book);
    }

    private void applyForm(Book book, String title, String isbn, Integer publicationYear,
                           Long categoryId, List<Long> authorIds) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Kategorija nije pronadjena."));
        List<Author> authors = authorIds == null ? List.of() : authorRepository.findAllById(authorIds);

        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublicationYear(publicationYear);
        book.setCategory(category);
        book.setAuthors(new HashSet<>(authors));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BookForm toForm(Book book) {
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setTitle(book.getTitle());
        form.setIsbn(book.getIsbn());
        form.setPublicationYear(book.getPublicationYear());
        form.setCategoryId(book.getCategory() == null ? null : book.getCategory().getId());
        form.setAuthorIds(book.getAuthors().stream().map(Author::getId).toList());
        return form;
    }

    @Transactional(readOnly = true)
    public BookApiResponse toResponse(Book book) {
        return new BookApiResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getCategory() == null ? null : book.getCategory().getId(),
                book.getCategory() == null ? null : book.getCategory().getName(),
                book.getAuthors().stream().map(Author::getId).toList(),
                book.getAuthors().stream().map(Author::getFullName).toList()
        );
    }
}
