package com.hyperionml.mapper;

import com.hyperionml.pojo.Class;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Select("select * from c_class where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "classname", property = "classname"),
            @Result(column = "id", property = "studentList", javaType = List.class,
                    many = @Many(select = "com.hyperionml.mapper.StudentMapper.selectStudentByCid"))
    })
    Class selectClassById(Integer id);
}
