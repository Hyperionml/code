package com.jrj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    @Test
    public void test1(){

    }

    try {
        String processName = "notepad.exe"; // 示例进程名
        ProcessBuilder processBuilder = new ProcessBuilder(
                "cmd.exe", "/c", "tasklist | findstr " + processName
        );
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            // 假设输出格式为 "notepad.exe                 1234 Console                    1     20,064 K"
            // 这里需要根据你的系统输出格式来调整正则表达式
            String pidPattern = "(\\d+)\\s+Console";
            Pattern pattern = Pattern.compile(pidPattern);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String pid = matcher.group(1);
                System.out.println("PID: " + pid);
                // 注意：这里只取第一个匹配的PID，如果有多个同名进程，需要进一步处理
                break;
            }
        }

        process.waitFor();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }


}


