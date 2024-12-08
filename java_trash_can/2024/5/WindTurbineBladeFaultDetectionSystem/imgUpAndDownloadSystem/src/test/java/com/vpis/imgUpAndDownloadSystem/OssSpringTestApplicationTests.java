package com.vpis.imgUpAndDownloadSystem;

import com.vpis.imgUpAndDownloadSystem.controller.OssController;
import com.vpis.imgUpAndDownloadSystem.utils.Base64Utils;
import com.vpis.imgUpAndDownloadSystem.utils.OssUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
class OssSpringTestApplicationTests {


    //@Test
    void contextLoads() throws IOException {
        //ossController.OssUpload("D:\\文件过渡\\1.jpeg");
    }

    //@Test
    void testMassUpload() throws IOException {
        //ossUtils.massUpload();
    }

    //@Test
    void test() {
        new File("./temp");
    }

    //@Test
    void testSaveBase64Image() throws IOException {

        //Base64Utils.saveBase64Image(url, "D:\\文件过渡\\temp", "1.jpeg");
    }

    //@Test
    void testDelete() {
        OssUtils.delete("Image_1702781169097.jpg");
    }
}
