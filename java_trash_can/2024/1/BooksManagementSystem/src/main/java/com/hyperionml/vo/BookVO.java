package com.hyperionml.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVO {
    private String name;
    private String author;
    private String press;
    private int number;
    private int state;
}
