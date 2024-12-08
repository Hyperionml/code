package com.hyperionml.mapper;

import com.hyperionml.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from s_student where id = #{id}")
    Student selectStudentById(Integer id);

    @Update("update s_student set name = #{name}, age = #{age}, cid = #{cid} where id = #{id}")
    int updateStudent(Student student);

    @Select("select * from s_student where cid = #{cid}")
    List<Student> selectStudentByCid(Integer cid);
}
