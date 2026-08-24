package com.example.isalibrary.dto;

import java.util.List;

public record BookApiResponse(
        Long id,
        String title,
        String isbn,
        Integer publicationYear,
        Long categoryId,
        String categoryName,
        List<Long> authorIds,
        List<String> authors
) {}
