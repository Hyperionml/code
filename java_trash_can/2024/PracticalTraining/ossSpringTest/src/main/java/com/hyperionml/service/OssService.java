package com.hyperionml.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyperionml.mapper.ImgdbMapper;
import com.hyperionml.pojo.img;
import com.hyperionml.utils.Base64Utils;
import com.hyperionml.utils.OssUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class OssService {

    @Autowired
    private OssUtils ossUtils;
    @Autowired
    private Base64Utils base64Utils;
    @Autowired
    private ImgdbMapper imgdbMapper;

    public void upload(ArrayList<img> imgs) throws Exception {
        //ossUtils.massUpload(url);
        imgs.forEach(img -> {
            try {
                base64Utils.saveBase64Image(img.getUrl(), "D:\\文件过渡\\temp", img.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ossUtils.massUpload(imgs);
        ArrayList<String> urls = ossUtils.massGetImgDownloadUrl(imgs);
        // 遍历stringList，并将值赋给objectList中每个对象的name属性
        for (int i = 0; i < Math.min(urls.size(), imgs.size()); i++) {
            imgs.get(i).setUrl(urls.get(i));
        }
        imgdbMapper.massInsert(imgs);
    }

    public void download(String filePath,String... fileNameList) {
        ossUtils.massDownload(filePath, fileNameList);
    }

    public List<img> listAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<img> imgList = imgdbMapper.findAllimgs();
        PageInfo<img> pageInfo = new PageInfo<>(imgList);
        List<img> imgs = pageInfo.getList();
        return imgs;
    }

}
