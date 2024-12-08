package com.hyperionml;

import com.hyperionml.mapper.ClassMapper;
import com.hyperionml.mapper.StudentMapper;
import com.hyperionml.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Eehomework4ApplicationTests {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ClassMapper classMapper;

    @Test
    void testSelectStudentById() {
        System.out.println(studentMapper.selectStudentById(2));
    }

    @Test
    void testUpdateStudentById(){
        studentMapper.updateStudent(new Student(4, "李雷", 21, 1));
    }

    @Test
    void testSelectClass(){
        System.out.println("\n" + classMapper.selectClassById(2) + "\n");
    }

}
