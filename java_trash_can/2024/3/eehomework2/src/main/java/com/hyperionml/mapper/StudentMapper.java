package com.hyperionml.mapper;

import com.hyperionml.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> select(String name, String major);

    List<Student> listBeforeId(Integer id);

}
