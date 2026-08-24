package com.example.isalibrary.repository;

import com.example.isalibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
