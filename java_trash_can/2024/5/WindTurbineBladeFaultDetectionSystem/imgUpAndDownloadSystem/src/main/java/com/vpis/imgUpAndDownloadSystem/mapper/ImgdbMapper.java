package com.vpis.imgUpAndDownloadSystem.mapper;

import com.vpis.imgUpAndDownloadSystem.pojo.img;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ImgdbMapper {

    int massInsert(ArrayList<img> imgs);

    List<img> findAllimgs();

    @Delete("delete from imgdb.imgtb where name = #{imgName}")
    int deleteByName(String imgName);

    int massDeleteByNames(List<String> imgNames);
}
