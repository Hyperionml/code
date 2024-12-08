package com.hyperionml.mapper;

import com.hyperionml.pojo.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {


    List<Book> list(String name, String author, String press);

    @Insert("insert into book (name, author, press, number, state) values (#{name}, #{author}, #{press}, #{number}, #{state})")
    int insertBook(Book book);

    @Select("select * from book where id = #{id}")
    Book getBookById(Integer id);

    int updateBookById(Book book);

    @Delete("delete from book where id = #{id}")
    int deleteBookById(Integer id);
}
