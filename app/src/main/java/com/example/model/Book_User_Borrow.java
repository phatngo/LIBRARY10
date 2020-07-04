package com.example.model;

import java.util.Date;

public class Book_User_Borrow {
    private String email;
    public String bookName;
    private String ISBN;
    private String date;

    public Book_User_Borrow() {
    }

    public String getBookName() {
        return bookName;
    }

    public Book_User_Borrow(String email, String bookName, String ISBN, String date) {
        this.email = email;
        this.bookName = bookName;
        this.ISBN = ISBN;
        this.date = date;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Book_User_Borrow(String email, String ISBN, String date) {
        this.email = email;
        this.ISBN = ISBN;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book_User_Borrow{}";
    }
}
