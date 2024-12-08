package com.jrj;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.jrj.HuffmanCode.*;
import static com.jrj.TreeNode.preOrder;

/**
 * @author jin_run_jun
 * @date 2024/5/27 9:35
 */
public class text {
    public static void main(String[] args) throws IOException {
        String str = null;
        try {
            // 文件路径
            String filePath = "E:\\数据结构与算法\\综合实验\\FinalExam\\HuffManTree\\Original.txt";
            // 使用FileInputStream打开文件
            FileInputStream fis = new FileInputStream(filePath);
            // 使用InputStreamReader将字节流转换为字符流，以便正确处理字符编码
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8"); // 注意指定正确的字符编码

            // 使用StringBuilder来构建字符串
            StringBuilder contentBuilder = new StringBuilder();
            char[] buffer = new char[1024];
            int read;
            while ((read = isr.read(buffer)) != -1) {
                contentBuilder.append(buffer, 0, read);
            }

            // 关闭资源
            isr.close();
            fis.close();

            // 将StringBuilder转换为String
            str = contentBuilder.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("str = " + str);

        List<TreeNode> nodes = getNodes(str);
        System.out.println("每个字符对应的频数："+nodes);
        System.out.println();

        TreeNode treeNode = creatHuffmanTree(nodes);
        System.out.println("对应的哈夫曼树(先序遍历)为：");
        preOrder(treeNode);
        System.out.println();

        Map<Character, String> codes = getCodes(treeNode);
        System.out.println("编码规则为："+codes);
        System.out.println();

        String s = encodeString(str);
        System.out.println("最终编码为："+s);
        System.out.println();

        System.out.println("编译后存入text.txt");
        String decode = decode(s, treeNode);
        File file = new File("E:\\数据结构与算法\\综合实验\\FinalExam\\HuffManTree\\text.txt");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(decode);

        fileWriter.close();
    }
}
