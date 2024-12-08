package com.hyperionml.mapper;

import com.hyperionml.pojo.order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper {
    List<order> findOrderWithProduct(int id);
}
