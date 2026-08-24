package com.example.isalibrary.rest;

import com.example.isalibrary.dto.BookApiRequest;
import com.example.isalibrary.dto.BookApiResponse;
import com.example.isalibrary.model.Book;
import com.example.isalibrary.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookApiResponse> all() {
        return bookService.findAll().stream().map(bookService::toResponse).toList();
    }

    @GetMapping("/{id}")
    public BookApiResponse one(@PathVariable Long id) {
        return bookService.toResponse(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookApiResponse> create(@RequestBody BookApiRequest request) {
        Book saved = bookService.saveApi(null, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.toResponse(saved));
    }

    @PutMapping("/{id}")
    public BookApiResponse update(@PathVariable Long id, @RequestBody BookApiRequest request) {
        return bookService.toResponse(bookService.saveApi(id, request));
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        bookService.delete(id);
        return Map.of("message", "Knjiga je obrisana.");
    }
}
