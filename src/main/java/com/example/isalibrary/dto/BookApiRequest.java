package com.example.isalibrary.dto;

import java.util.ArrayList;
import java.util.List;

public class BookApiRequest {
    private String title;
    private String isbn;
    private Integer publicationYear;
    private Long categoryId;
    private List<Long> authorIds = new ArrayList<>();

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
