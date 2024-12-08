package com.vpis.imgUpAndDownloadSystem.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vpis.imgUpAndDownloadSystem.mapper.ImgdbMapper;
import com.vpis.imgUpAndDownloadSystem.pojo.PageData;
import com.vpis.imgUpAndDownloadSystem.pojo.Result;
import com.vpis.imgUpAndDownloadSystem.pojo.img;
import com.vpis.imgUpAndDownloadSystem.utils.Base64Utils;
import com.vpis.imgUpAndDownloadSystem.utils.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OssService {

    @Autowired
    private ImgdbMapper imgdbMapper;

    @Value("${TempFilesPath}")
    private String tempPath;

    public void upload(ArrayList<img> imgs) throws IOException {
        imgs.forEach(img -> {
            try {
                Base64Utils.saveBase64Image(img.getUrl(), tempPath, img.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        OssUtils.massUpload(imgs);
        ArrayList<String> urls = OssUtils.massGetImgDownloadUrl(imgs);
        // 遍历stringList，并将值赋给objectList中每个对象的name属性
        for (int i = 0; i < Math.min(urls.size(), imgs.size()); i++) {
            imgs.get(i).setUrl(urls.get(i));
        }
        imgdbMapper.massInsert(imgs);

        //删除缓存
        Base64Utils.deleteDirectory(new File(tempPath));
    }

    public void download(String filePath,String... fileNameList) {
        OssUtils.massDownload(filePath, fileNameList);
    }

    public PageData listAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<img> imgList = imgdbMapper.findAllimgs();
        PageInfo<img> pageInfo = new PageInfo<>(imgList);
        return new PageData(pageInfo.getList(), pageInfo.getTotal());
    }

    @Transactional
    public Result deleteByName(String imgName) {
        int code = imgdbMapper.deleteByName(imgName);
        int deleted = OssUtils.delete(imgName);
        if(code != 0 && deleted != 0){
            return Result.success("成功删除图片");
        }
        //回滚
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return Result.error("未知原因图片删除失败");
    }

    @Transactional
    public Result massDeleteByNames(String[] imgNames) {
        int code = imgdbMapper.massDeleteByNames(Arrays.asList(imgNames));
        int deleted = OssUtils.massDelete(imgNames);
        if(code != 0 && deleted != 0){
            return Result.success("成功删除全部图片");
        }
        //回滚
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return Result.error("未知原因图片删除失败");
    }
}
