package com.hyperionml;

import com.aliyuncs.exceptions.ClientException;
import com.hyperionml.controller.OssController;
import com.hyperionml.utils.Base64Utils;
import com.hyperionml.utils.OssUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@SpringBootTest
class OssSpringTestApplicationTests {

    @Autowired
    private OssController ossController;
    @Autowired
    private OssUtils ossUtils;
    @Autowired
    private Base64Utils base64Utils;

    @Value("testurl")
    String url;

    @Test
    void contextLoads() throws IOException {
        //ossController.OssUpload("D:\\文件过渡\\1.jpeg");
    }

    @Test
    void testMassUpload() throws IOException {
        //ossUtils.massUpload();
    }

    @Test
    void testGetDate() {
        String nowDate = ossUtils.getNowDate();
        System.out.println(nowDate);
    }

    @Test
    void testDownload() {
        ossUtils.testDownload();
    }

    @Test
    void test() {
        new File("./temp");
    }

    @Test
    void testSaveBase64Image() throws IOException {

        base64Utils.saveBase64Image(url, "D:\\文件过渡\\temp", "1.jpeg");
    }
}
