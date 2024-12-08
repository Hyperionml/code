package com.example.myfolderapp.service;

import ai.djl.Application;
import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.Shape;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import com.alibaba.fastjson2.JSON;
import com.example.myfolderapp.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Service
public class FolderService {

    public Result getFolderContents(String datetime) {
        log.info("获取数据调用，参数：" + datetime);
        String folderPath = "/root/workspace/bearingData/" + datetime;
        folderPath = folderPath.replace(".", "/");
        File folder = new File(folderPath);
        HashMap<Integer, String> re = new HashMap<>();

        if (!folder.exists() && !folder.isDirectory()){
            log.error("文件路径不存在");
            return Result.err("文件路径不存在");
        }

        File[] files = folder.listFiles();
        if (files == null){
            log.error("文件为空");
            return Result.err("文件为空");
        }

        for (int i = 0; i < files.length; i++) {
            StringBuilder str = new StringBuilder();
            try (FileInputStream fis = new FileInputStream(files[i]);
                 Scanner scanner = new Scanner(fis, StandardCharsets.UTF_8)) {

                while (scanner.hasNextLine()) {
                    str.append(scanner.nextLine()).append("\n");
                }

            } catch (IOException e) {
                log.error("未知错误");
                return  Result.err("未知错误");
            }
            re.put(i + 1, str.toString());
        }
        log.info("成功获取数据");
        return Result.success("成功获取文件", re.toString());
    }

    
    // 从文本文件中读取浮点数并返回列表
    private static List<Float> readFileToFloatList(String filePath) throws IOException {
        List<Float> floatList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                for (String number : numbers) {
                    floatList.add(Float.parseFloat(number));
                }
            }
        }
        return floatList;
    }
}