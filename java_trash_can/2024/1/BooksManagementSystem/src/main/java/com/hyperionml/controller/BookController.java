package com.hyperionml.controller;

import com.hyperionml.pojo.Book;
import com.hyperionml.pojo.Result;
import com.hyperionml.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders="*")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Result list(String name, String author, String press){
        return Result.success(bookService.list(name, author, press));
    }

    @PostMapping("/save")
    public Result addBook(@RequestBody Book book){
        return Result.success(bookService.addBook(book));
    }

    @GetMapping("/{id}")
    public Result selectBookById(@PathVariable Integer id){
        return Result.success(bookService.selectBookById(id));
    }

    @PutMapping
    public Result changeBookInf(@RequestBody Book book){
        return Result.success(bookService.changeBookInfById(book));
    }

    @DeleteMapping("/{id}")
    public Result deleteBookById(@PathVariable Integer id){
        int res = bookService.deleteBookById(id);
        if(res != 0){
            return Result.success();
        }else {
            return Result.error("删除失败");
        }
    }
}
