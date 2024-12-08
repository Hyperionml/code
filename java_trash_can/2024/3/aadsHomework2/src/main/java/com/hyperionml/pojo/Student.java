package com.hyperionml.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Student {
    private String name;
    private int id;
    private double aadsScore;
    private double enScore;
    private double maScore;
    private double spScore;
    private double allScore;

    public Student(String name, int id, double aadsScore, double enScore, double maScore, double spScore) {
        this.name = name;
        this.id = id;
        this.aadsScore = aadsScore;
        this.enScore = enScore;
        this.maScore = maScore;
        this.spScore = spScore;
        this.allScore = aadsScore + enScore + maScore + spScore;
    }
}
