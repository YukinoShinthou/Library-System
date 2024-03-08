package com.example.demo.project2.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "person_id" , referencedColumnName = "id")
    private Person owner;
    @Column(name = "book_name")
    @NotEmpty(message = "Enter the name of book")
    private String bookName;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date takenAt;

    public Book() {
    }

    public Book(int id, Person owner, String bookName, Date releaseDate, Date takenAt) {
        this.id = id;
        this.owner = owner;
        this.bookName = bookName;
        this.releaseDate = releaseDate;
        this.takenAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", owner=" + owner +
                ", bookName='" + bookName + '\'' +
                ", releaseDate=" + releaseDate +
                ", takenAt=" + takenAt +
                '}';
    }


}
