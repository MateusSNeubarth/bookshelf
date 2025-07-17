package com.mateusneubarth.bookshelf.repository;

import com.mateusneubarth.bookshelf.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Long> {
}
