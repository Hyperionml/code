package com.hyperionml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    private Integer id;
    private String classname;
    private List<Student> studentList;
}
