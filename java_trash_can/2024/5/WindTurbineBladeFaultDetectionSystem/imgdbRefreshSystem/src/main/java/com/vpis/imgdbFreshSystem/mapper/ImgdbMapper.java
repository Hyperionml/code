package com.vpis.imgdbFreshSystem.mapper;

import com.vpis.imgdbFreshSystem.pojo.img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ImgdbMapper {

    @Select("select * from imgdb.imgtb")
    ArrayList<img> getAllImg();

    int updateImgUrl(img imgs);
}
