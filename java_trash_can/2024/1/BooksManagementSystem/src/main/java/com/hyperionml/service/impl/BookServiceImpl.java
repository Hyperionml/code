package com.hyperionml.service.impl;

import com.hyperionml.mapper.BookMapper;
import com.hyperionml.pojo.Book;
import com.hyperionml.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> list(String name, String author, String press) {
        return bookMapper.list(name, author, press);
    }

    @Override
    public int addBook(Book book) {
        return bookMapper.insertBook(book);
    }

    @Override
    public Book selectBookById(Integer id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public int changeBookInfById(Book book) {
        return bookMapper.updateBookById(book);
    }

    @Override
    public int deleteBookById(Integer id) {
        return bookMapper.deleteBookById(id);
    }
}
