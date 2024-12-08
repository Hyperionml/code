package com.hyperionml.controller;

import com.hyperionml.pojo.Result;
import com.hyperionml.pojo.img;
import com.hyperionml.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vpis")
@CrossOrigin("*")
public class OssController {

    @Autowired
    private OssService ossService;

    @PutMapping("/upload")
    public void OssUpload(@RequestBody ArrayList<img> imgs) throws Exception {
        ossService.upload(imgs);
        //System.out.println(Arrays.toString(imgs));
    }

    @GetMapping("/dawnload")
    public void download(String filePath,String... fileNameList) throws IOException{
        ossService.download(filePath, fileNameList);
    }

    @GetMapping("/test")
    private void test(){
        System.out.println("测试");
    }

    @GetMapping("/page")
    private Result listAllImgs(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return Result.success(ossService.listAll(pageNum,pageSize));
    }

}
