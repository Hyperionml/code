import com.hyperionml.pojo.TreeNode;
import com.hyperionml.utils.AadsUtils;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;


public class ClassTest {

    @Test
    public void test1() throws Throwable {
        System.out.println(AadsUtils.evaluateExpression("2 ^ 2 + 1 * 2 "));
    }

    @Test
    public void test2() {
        String infix = "1+2*(3^4-5)^(6+7*8)-9";
        // 注意：上面的中缀表达式包含了幂运算，这只是一个示例，实际实现中可能需要特殊处理
        List<String> postfix = AadsUtils.convert(infix);
        System.out.println("Postfix: " + String.join(" ", postfix));
    }

    @Test
    public void test3() {
        AadsUtils.dancePartnerProblem();
    }

    @Test
    public void test4() {
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

        //System.out.println("编译后存入text.txt");
        String decode = AadsUtils.HuffmanCode.decode(s, treeNode);
        System.out.println(decode);

    }

    @Test
    public void test5() {
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
        aovGraph.dijkstra(0);
    }

    @Test
    public void test6() {
        AadsUtils.CriticalPath criticalPath = new AadsUtils.CriticalPath();
        criticalPath.createGraph2();
        criticalPath.criticalPath();
    }

    @Test
    public void test7() {
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
        undirectedGraph.Kruskal(integer -> System.out.println(integer/10 + " " + integer%10));
    }

    public static void main(String[] args) {
        AadsUtils.dancePartnerProblem();
    }
}
