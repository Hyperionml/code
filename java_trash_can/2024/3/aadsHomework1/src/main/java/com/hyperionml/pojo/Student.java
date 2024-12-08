package com.hyperionml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Double.compare(aadsScore, student.aadsScore) == 0 && Double.compare(enScore, student.enScore) == 0 && Double.compare(maScore, student.maScore) == 0 && Double.compare(spScore, student.spScore) == 0 && Double.compare(allScore, student.allScore) == 0 && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, aadsScore, enScore, maScore, spScore, allScore);
    }*/
}
