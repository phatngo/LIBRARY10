package com.example.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Book implements Serializable {
    private String ISBN;
    private String bookName;
    private int publishedYear;
    private String author;
    private int quantity;
    private String genre;

    //Tab 3
    public Book(String ISBN, String author, String bookName, String genre, int publishedYear,  int quantity) {
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.publishedYear = publishedYear;
        this.author = author;
        this.quantity = quantity;
        this.genre = genre;
    }

    public Book(String ISBN, String bookName, String author, String genre) {
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Book () {
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.getISBN()+","+this.getBookName();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ISBN", this.getISBN());
        result.put("author", this.getAuthor());
        result.put("bookName", this.getBookName());
        result.put("genre", this.getGenre());
        result.put("publishedYear", this.getPublishedYear());
        result.put("quantity", this.getQuantity());
        return result;
    }
}
