package com.example.myfolderapp;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class MyFolderAppApplicationTests {

    @Test
    void contextLoads() {
    }

    //@Test
    void test() {
        String inputFilePath = "D:\\workspace\\python\\2024\\10\\Code\\requirements.txt"; // 输入文件路径
        String outputFilePath = "D:\\workspace\\python\\2024\\10\\Code\\requirements1.txt"; // 输出文件路径，可以是同一个文件路径以覆盖原文件

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // 查找@符号的索引位置
                int atIndex = line.indexOf('@');
                if (atIndex != -1) {
                    // 如果找到@符号，则截取@之前的部分
                    line = line.substring(0, atIndex);
                }
                // 将处理后的行写入输出文件
                writer.write(line);
                writer.newLine(); // 不要忘记写入换行符
            }

            System.out.println("处理完成！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Test
    void test1() {
        // 原始文件路径
        Path sourcePath = Paths.get("D:\\workspace\\python\\2024\\10\\Code\\requirements.txt");
        // 目标文件路径（可以是同一个文件以覆盖原始内容）
        Path targetPath = Paths.get("D:\\workspace\\python\\2024\\10\\Code\\requirements1.txt");
        // 要删除的字符串
        String targetString = "anaconda";

        try (Stream<String> lines = Files.lines(sourcePath);
             BufferedWriter writer = Files.newBufferedWriter(targetPath)) {

            // 使用Java 8的Stream API过滤掉包含目标字符串的行
            List<String> filteredLines = lines
                    .filter(line -> !line.contains(targetString))
                    .collect(Collectors.toList());

            // 将过滤后的行写回文件
            for (String line : filteredLines) {
                writer.write(line);
                writer.newLine();
            }

            // 如果你想覆盖原始文件，可以将目标文件重命名为原始文件名
            // Files.move(targetPath, sourcePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("处理完成！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test3() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "test1");
        map.put(2, "test2");
        System.out.println(map);

        String str = JSON.toJSONString(map);
        System.out.println(str);
    }
}
