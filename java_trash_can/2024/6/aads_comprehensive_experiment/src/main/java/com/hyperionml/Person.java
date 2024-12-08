package com.hyperionml;

import lombok.Data;

@Data
public class Person {
    private String name;
    private Boolean isMen;
    private int completedCount;

    public Person() {
        completedCount = 0;
    }
}
