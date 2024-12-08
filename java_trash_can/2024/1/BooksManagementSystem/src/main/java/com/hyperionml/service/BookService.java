package com.hyperionml.service;

import com.hyperionml.pojo.Book;

import java.util.List;

public interface BookService {

    List<Book> list(String name, String author, String press);

    int addBook(Book book);

    Book selectBookById(Integer id);

    int changeBookInfById(Book book);

    int deleteBookById(Integer id);
}
