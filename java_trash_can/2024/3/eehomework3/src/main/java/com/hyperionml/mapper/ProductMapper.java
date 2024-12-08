package com.hyperionml.mapper;

import com.hyperionml.pojo.product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<product> findProductById(int id);
}
