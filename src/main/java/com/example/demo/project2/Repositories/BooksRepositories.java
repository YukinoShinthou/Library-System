package com.example.demo.project2.Repositories;



import com.example.demo.project2.Models.Book;
import com.example.demo.project2.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepositories extends JpaRepository<Book, Integer> {

    List<Book> findByOwner(Person owner);

    List<Book> findByBookNameStartingWith(String startingWith);

}
