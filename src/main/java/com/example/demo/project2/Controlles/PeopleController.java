package com.example.demo.project2.Controlles;


import com.example.demo.project2.Models.Person;
import com.example.demo.project2.Services.BooksService;
import com.example.demo.project2.Services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final BooksService booksService;
    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String AllPeople(Model model){
        model.addAttribute("people" , peopleService.findAll());

        return "people/showAll";
    }

    @GetMapping("/{id}")
    public String showOnePerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOnePerson(id));
        Person owner = peopleService.findOnePerson(id);
        model.addAttribute("books", booksService.foundByPersonId(owner));

        return "people/showOnePerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/newPerson";
    }
    @PostMapping()
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                                  BindingResult bindingResult  ){
        if (bindingResult.hasErrors()){
            return "people/newPerson";
        }
        else {

            person.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            System.out.println(person);
            peopleService.save(person);
            return "redirect:/people";
        }
    }

    @GetMapping("/edit/{id}")
    public String editPerson(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOnePerson(id));
        return "people/editPerson";
    }
    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "people/editPerson";
        }
        else {
            peopleService.update(id, person);
            return "redirect:/people/{id}";
        }
    }

    @GetMapping("/search")
    public String searchPage(){
        return "/people/searchPerson";
    }


    @PostMapping("/search")
    public String searchPerson(@RequestParam("id")int id, Model model){
        if(peopleService.findOnePerson(id) == null){
            //if the result is null or user is not found
            model.addAttribute("SearchId", id);
            model.addAttribute("message", true);
        }else {
            //found user
            model.addAttribute("foundPerson", peopleService.findOnePerson(id));
            model.addAttribute("message", false);
        }
        return "people/searchPerson";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people/search";
    }
}
