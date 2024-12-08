package com.vpis.imgUpAndDownloadSystem.controller;

import com.vpis.imgUpAndDownloadSystem.pojo.Result;
import com.vpis.imgUpAndDownloadSystem.pojo.img;
import com.vpis.imgUpAndDownloadSystem.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/vpis")
@CrossOrigin("*")
public class OssController {

    @Autowired
    private OssService ossService;

    @PutMapping("/upload")
    public void OssUpload(@RequestBody ArrayList<img> imgs) throws IOException {
        ossService.upload(imgs);
    }

    @GetMapping("/dawnload")
    public void download(String filePath,String... fileNameList) {
        ossService.download(filePath, fileNameList);
    }

    @GetMapping("/page")
    public Result listAllImgs(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return Result.success(ossService.listAll(pageNum,pageSize));
    }

    @DeleteMapping("/delete")
    public Result deleteByName(@RequestParam String imgName){
        return ossService.deleteByName(imgName);
    }

    @DeleteMapping("/massDelete")
    public Result massDeleteByNames(@RequestParam String[] imgNames){
        return ossService.massDeleteByNames(imgNames);
    }

}
