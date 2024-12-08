package com.hyperionml.mapper;

import com.hyperionml.pojo.img;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jmx.export.annotation.ManagedOperation;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ImgdbMapper {

    int massInsert(ArrayList<img> imgs);

    List<img> findAllimgs();
}
