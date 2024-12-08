package com.vpis.imgdbFreshSystem.service;

import com.vpis.imgdbFreshSystem.mapper.ImgdbMapper;
import com.vpis.imgdbFreshSystem.pojo.img;
import com.vpis.imgdbFreshSystem.utils.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseRefreshService {

    @Autowired
    private ImgdbMapper imgdbMapper; // 假设你有一个用于与数据库交互的仓库或DAO类
    @Autowired
    private OssUtils ossUtils;

    @Scheduled(fixedDelay = 1000L * 3600 * 24 * 30) // 每30天执行一次
    //@Scheduled(fixedDelay = 1000L * 60)
    public void refreshDatabase() throws Throwable {
        ArrayList<img> allImg = imgdbMapper.getAllImg();
        ArrayList<String> allImgUrl = ossUtils.FreshAllImgUrl(allImg);
        for (int i = 0; i < Math.min(allImgUrl.size(), allImg.size()); i++) {
            imgdbMapper.updateImgUrl(new img(allImg.get(i).getName(), allImg.get(i).getUptime(), allImgUrl.get(i)));
        }
    }

}