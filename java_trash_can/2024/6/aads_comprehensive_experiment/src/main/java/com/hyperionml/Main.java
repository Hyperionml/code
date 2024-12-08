package com.hyperionml;

import com.hyperionml.pojo.TreeNode;
import com.hyperionml.utils.AadsUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Throwable {

        System.out.println("欢迎使用算法与数据结构综合实验测试程序");

        while (true){
            help();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if ("1".equals(input)){

            }
            else if("clear".equals(input)){
                for (int i = 0; i < 20; i++) {
                    System.out.println();
                }
            }

            else if("exit".equals(input)){
                break;
            }

            else if("2".equals(input)){
                while (true){
                    System.out.println("请输入需要计算的一元多项式（不需要输入等号,输入exit退出程序）");
                    String str = scanner.nextLine();
                    if("exit".equals(str)){
                        break;
                    }
                    double rs = AadsUtils.evaluateExpression(str);
                    System.out.println("您输入的算式为：" + str + " ,计算结果为：" + rs);
                }
            }

            else if("3".equals(input)){
                while (true){
                    System.out.println("请输入需要计算的算术表达式（不需要输入等号,输入exit退出程序）");
                    String str = scanner.nextLine();
                    if("exit".equals(str)){
                        break;
                    }
                    List<String> rs = AadsUtils.convert(str);
                    System.out.println("您输入的算式为：" + str + " 其后缀表达式为: " + String.join("", rs));
                }
            }

            else if("4".equals(input)){
                AadsUtils.dancePartnerProblem();
            }

            else if("5".equals(input)){
                String str = null;
                try {
                    // 文件路径
                    String filePath = "./src/main/java/com/hyperionml/txt/test.txt";
                    // 使用FileInputStream打开文件
                    FileInputStream fis = new FileInputStream(filePath);
                    // 使用InputStreamReader将字节流转换为字符流，以便正确处理字符编码
                    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8); // 注意指定正确的字符编码

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

                List<TreeNode> nodes = AadsUtils.HuffmanCode.getNodes(str);
                System.out.println("每个字符对应的频数："+nodes);
                System.out.println();

                TreeNode treeNode = AadsUtils.HuffmanCode.creatHuffmanTree(nodes);

                Map<Character, String> codes = AadsUtils.HuffmanCode.getCodes(treeNode);
                System.out.println("编码规则为："+codes);
                System.out.println();

                String s = AadsUtils.HuffmanCode.encodeString(str);
                System.out.println("最终编码为："+s);
                System.out.println();

                String decode = AadsUtils.HuffmanCode.decode(s, treeNode);
                System.out.println(decode);
            }

            else if("6".equals(input)){
                System.out.println("由于地图输入较为困难，所以本程序中的案例地图已经规定");
                char[] list = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n' ,'o' ,'p'};
                int[][] edges = new int[][]{
                        {0, 2, 4, -1, 7, 9, -1, 5, -1, 6, 6, 7, -1, 9, 1, -1},
                        {2, 0, -1, 6, -1, 9, 3, 5, -1, 6, 2, 7, -1, 9, 1, 3},
                        {4, -1, 0, 6, 7, 9, -1, 5, 7, 6, 2, -1, 8, 9, 1, 3},
                        {-1, 6, 6, 0, 7, 9, 3, 5, 7, 6, 2, 7, 8, 9, 1, 3},
                        {7, -1, 7, 7, 0, 9, 3, -1, 7, 6, 2, 7, 8, 9, 1, 3},
                        {9, 9, 9, 9, 9, 0, 3, 5, 7, 6, -1, 7, 8, 9, 1, 3},
                        {-1, 3, -1, 3, 3, 3, 0, 5, 7, 6, 2, 7, 8, -1, 1, 3},
                        {5, 5, 5, 5, -1, 5, 5, 0, 7, 6, -1, 7, 8, 9, 1, 3},
                        {-1, -1, 7, 7, 7, 7, 7, 7, 0, 6, 2, 7, 8, 9, 1, 3},
                        {6, 6, 6, 6, 6, 6, 6, 6, 6, 0, -1, 7, 8, 9, 1, 3},
                        {6, 2, 2, 2, 2, -1, 2, -1, 2, -1, 0, 7, 8, -1, 1, 3},
                        {7, 7, -1, 7, 7, 7, 7, 7, 7, 7, 7, 0, -1, 9, 1, 7},
                        {-1, -1, 8, 8, 8, 8, 8, 8, 8, 8, -1, 7, 0, 9, 1, 3},
                        {9, 9, 9, 9, 9, 9, -1, 9, 9, 9, -1, 9, 9, 0, 1, -1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 4},
                        {-1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 7, 3, -1, 4, 0}
                };
                AadsUtils.AOVGraph aovGraph = new AadsUtils.AOVGraph(list, edges);
                while (true){
                    System.out.println("输入a进入边权查询模式，b进入最短路径查询模式，exit退出当前程序");
                    String input1 = scanner.nextLine();
                    if("a".equals(input1)){
                        System.out.println("输入你想要查询的边的起点");
                        char start = scanner.nextLine().charAt(0);
                        int s = find(list, start);
                        System.out.println("输入你想要查询的边的起点");
                        char end = scanner.nextLine().charAt(0);
                        int e = find(list, end);
                        System.out.println("该边的距离长度为：" + edges[s][e]);
                    }
                    else if("b".equals(input1)){
                        System.out.println("请输入你想要查询的起点");
                        char c = scanner.nextLine().charAt(0);
                        int index = find(list, c);
                        try {
                            aovGraph.dijkstra(index);
                        } catch (Exception ignored) {}
                    }
                    else if("exit".equals(input1)){
                        break;
                    }
                    else{
                        System.out.println("错误指令");
                    }
                }

            }

            else if("7".equals(input)){
                AadsUtils.CriticalPath criticalPath = new AadsUtils.CriticalPath();
                criticalPath.createGraph2();
                System.out.println("由于工程图输入困难，所以本案例是预先提供的工程图模型");
                while (true){
                    System.out.println("输入a进入边权查询模式，b输出关键路径和关键活动，exit退出当前程序");
                    String input1 = scanner.nextLine();
                    if("a".equals(input1)) {
                        Scanner sc = new Scanner(System.in);
                        System.out.println("请输入起点活动");
                        int x = sc.nextInt();
                        System.out.println("请输入终点活动");
                        int y = sc.nextInt();
                        AadsUtils.CriticalPath.EdgeNode firstEdge = criticalPath.getVexList().get(x).getFirstEdge();
                        int c = firstEdge.getAdjvex();
                        while (y != c) {
                            if (firstEdge.getNext() == null) {
                                break;
                            }
                            firstEdge = firstEdge.getNext();
                            c = firstEdge.getAdjvex();
                        }
                        if (y == c) {
                            System.out.println("该路径权值为：" + firstEdge.getWeight());
                        } else {
                            System.out.println("这两的点之间的这个方向没有路径");
                        }
                    }
                    else if("b".equals(input1)){
                        criticalPath.criticalPath();
                    }
                    else if("exit".equals(input1)){
                        break;
                    }
                    else{
                        System.out.println("错误指令");
                    }
                }
            }

            else if("8".equals(input)){
                char[] list = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n' ,'o' ,'p'};
                int[][] edges = new int[][]{
                        {0, 2, 4, 0, 7, 9, 0, 5, 0, 6, 6, 7, 0, 9, 1, 0},
                        {2, 0, 0, 6, 0, 9, 3, 5, 0, 6, 2, 7, 0, 9, 1, 3},
                        {4, 0, 0, 6, 7, 9, 0, 5, 7, 6, 2, 0, 8, 9, 1, 3},
                        {0, 6, 6, 0, 7, 9, 3, 5, 7, 6, 2, 7, 8, 9, 1, 3},
                        {7, 0, 7, 7, 0, 9, 3, 0, 7, 6, 2, 7, 8, 9, 1, 3},
                        {9, 9, 9, 9, 9, 0, 3, 5, 7, 6, 0, 7, 8, 9, 1, 3},
                        {0, 3, 0, 3, 3, 3, 0, 5, 7, 6, 2, 7, 8, 0, 1, 3},
                        {5, 5, 5, 5, 0, 5, 5, 0, 7, 6, 0, 7, 8, 9, 1, 3},
                        {0, 0, 7, 7, 7, 7, 7, 7, 0, 6, 2, 7, 8, 9, 1, 3},
                        {6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 7, 8, 9, 1, 3},
                        {6, 2, 2, 2, 2, 0, 2, 0, 2, 0, 0, 7, 8, 0, 1, 3},
                        {7, 7, 0, 7, 7, 7, 7, 7, 7, 7, 7, 0, 0, 9, 1, 7},
                        {0, 0, 8, 8, 8, 8, 8, 8, 8, 8, 8, 0, 0, 9, 1, 3},
                        {9, 9, 9, 9, 9, 9, 0, 9, 9, 9, 0, 9, 9, 0, 1, 0},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 4},
                        {0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 7, 3, 0, 4, 0}};
                AadsUtils.UndirectedGraph undirectedGraph = new AadsUtils.UndirectedGraph(list, edges);
                System.out.println("由于城市图输入困难，所以本案例是预先提供的城市图模型");
                while (true){
                    System.out.println("输入a进入边权查询模式，b生成最小生成树（即最经济的架设方法），exit退出当前程序");
                    String input1 = scanner.nextLine();
                    if("a".equals(input1)){
                        Scanner sc = new Scanner(System.in);
                        System.out.println("请输入起点城市");
                        int x = sc.nextInt();
                        System.out.println("请输入终点城市");
                        int y = sc.nextInt();
                        int i = undirectedGraph.getEdges()[x][y];
                        if(i == 0){
                            System.out.println("该两城市之间无法假设路线");
                        }else {
                            System.out.println("该两城市之间的建设路线的造价为：" + i);
                        }
                    }
                    else if ("b".equals(input1)){
                        System.out.println("该城市图的最小生成树（即最经济的架设方法）为：");
                        undirectedGraph.Kruskal(integer -> System.out.println(integer/10 + " " + integer%10));
                    }
                    else if("exit".equals(input1)){
                        break;
                    }
                    else{
                        System.out.println("错误指令");
                    }
                }

            }

        }
    }

    public static void help(){
        System.out.println("输入对应数字使用对应功能程序");
        System.out.println("1.输出帮助文档");
        System.out.println("2.一元多项式的代数运算");
        System.out.println("3.算术表达式求值");
        System.out.println("4.舞伴问题");
        System.out.println("5.哈夫曼编码");
        System.out.println("6.校园导航问题");
        System.out.println("7.关键路径问题");
        System.out.println("8.最小生成树问题");
        System.out.println("输入clear可以清空当前控制台，输入exit退出程序");
    }

    private static int find(char[] arr, char element){
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element){
                index = i;
                break;
            }
        }
        return index;
    }

}
