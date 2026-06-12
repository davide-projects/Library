package com.apulia.library.repository;

import com.apulia.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Rappresenta Il Database Astratto
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    public List<Book> findByTitleContainingIgnoreCase(String title);
    public List<Book> findByAuthorContainingIgnoreCase(String author);
    public List<Book> findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(String author, String title);
}
