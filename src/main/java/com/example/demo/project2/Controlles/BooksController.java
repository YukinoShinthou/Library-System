package com.example.demo.project2.Controlles;


import com.example.demo.project2.Models.Book;
import com.example.demo.project2.Models.Person;
import com.example.demo.project2.Services.BooksService;
import com.example.demo.project2.Services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleService peopleService;

    private final BooksService booksService;
    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String AllBooks(Model model){
        model.addAttribute("books" , booksService.findAll());

        return "books/showAll";
    }

    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOneBook(id));
        if(booksService.findOneBook(id).getOwner() != null) {
            model.addAttribute("owner", booksService.findOneBook(id).getOwner());
        }else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/showOneBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book person){
        return "books/newBook";
    }
    @PostMapping()
    public String createNewBook(@ModelAttribute("book") @Valid Book book,
                                  BindingResult bindingResult  ){
        if (bindingResult.hasErrors()){
            return "books/newBook";
        }
        else {
            booksService.save(book);
            return "redirect:/books/search";
        }
    }

    @GetMapping("/edit/{id}")
    public String editBook(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOneBook(id));
        return "books/editBook";
    }
    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/editBook";
        }
        else {
            booksService.update(id, book);
            return "redirect:/books";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }


    @PatchMapping("/books/{id}/assign")
    public String assignBook(@PathVariable("id") int id,@ModelAttribute("person") Person selectedPerson){
        Book book = booksService.findOneBook(id);
        book.setOwner(selectedPerson);
        book.setTakenAt(new Date());
        booksService.save(book);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/books/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        System.out.println(id);
        Book book = booksService.findOneBook(id);
        book.setOwner(null);
        book.setTakenAt(null);
        booksService.save(book);
        return "redirect:/books/{id}" ;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "/books/searchBook";
    }


    @PostMapping("/search")
    public String searchBooks(@RequestParam("bookName")String bookName, Model model){
        if(booksService.SearchBooks(bookName) == null || booksService.SearchBooks(bookName).isEmpty()){
            //if the result is null or books aren't found
            model.addAttribute("SearchName", bookName);
            model.addAttribute("message", true);
        }else {
            //books are found
            model.addAttribute("foundBooks", booksService.SearchBooks(bookName));
            model.addAttribute("message", false);
        }
        return "books/searchBook";
    }
}