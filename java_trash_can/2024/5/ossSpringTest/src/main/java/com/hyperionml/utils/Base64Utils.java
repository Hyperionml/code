package com.hyperionml.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class Base64Utils {
    public void saveBase64Image(String base64String, String directoryPath, String imageName) throws IOException {
        // 解码Base64字符串为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64String);

        // 构建完整的文件路径
        String filePath = Paths.get(directoryPath, imageName).toString();

        // 写入到文件
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
            fos.flush();
            System.out.println("Image saved successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving image: " + e.getMessage());
        }

        //删除缓存
        deleteDirectory(new File(filePath));

    }

    private static void deleteDirectory(File dir) throws IOException {
        if (dir.isDirectory()) {
            // 获取文件夹内的所有文件和子文件夹
            File[] files = dir.listFiles();
            if (files != null) {
                // 递归删除所有文件和子文件夹
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        // 目录现在是空的，所以可以删除
        if (!dir.delete()) {
            throw new IOException("Could not delete directory: " + dir.getAbsolutePath());
        }
    }

}
