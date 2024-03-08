package com.example.demo.project2.Services;


import com.example.demo.project2.Models.Book;
import com.example.demo.project2.Models.Person;
import com.example.demo.project2.Repositories.BooksRepositories;
import com.example.demo.project2.Repositories.PeopleRepositories;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BooksService {
    BooksRepositories booksRepositories;
    private final PeopleRepositories peopleRepositories;

    @Autowired
    public BooksService(BooksRepositories booksRepositories,
                        PeopleRepositories peopleRepositories) {
        this.booksRepositories = booksRepositories;
        this.peopleRepositories = peopleRepositories;
    }

    public List<Book> findAll(){
        return booksRepositories.findAll();
    }

    public Book findOneBook(int id){
        Optional<Book> foundBook = booksRepositories.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        book.setTakenAt(new Date());

        booksRepositories.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepositories.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepositories.deleteById(id);
    }

    @Transactional
    public List<Book> foundByPersonId(Person owner){
        return booksRepositories.findByOwner(owner);
    }

    @Transactional
    public List<Book> SearchBooks(String string){
        return booksRepositories.findByBookNameStartingWith(string);
    }

}
