package com.hyperionml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String name;
    private String author;
    private String press;
    private int number;
    private int state;
}
