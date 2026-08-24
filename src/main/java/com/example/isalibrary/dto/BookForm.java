package com.example.isalibrary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BookForm {
    private Long id;

    @NotBlank(message = "Naslov je obavezan.")
    private String title;

    @NotBlank(message = "ISBN je obavezan.")
    private String isbn;

    @NotNull(message = "Godina je obavezna.")
    private Integer publicationYear;

    @NotNull(message = "Kategorija je obavezna.")
    private Long categoryId;

    private List<Long> authorIds = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public List<Long> getAuthorIds() { return authorIds; }
    public void setAuthorIds(List<Long> authorIds) { this.authorIds = authorIds; }
}
