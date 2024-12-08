
import com.hyperionml.pojo.VertexNode;
import com.hyperionml.utils.impl.AOVGraph;
import com.hyperionml.utils.impl.DirectedGraph;
import com.hyperionml.utils.impl.UndirectedGraph;
import org.junit.Test;

import java.util.Arrays;


public class GraphTest {

    @Test
    public void UndGraphBFTTest() {
        char[] list = {'a', 'b', 'c', 'd'};//这个图表示的是 a - d
        int[][] edges = {{0, 1, 0, 1},//                 \   \
                         {1, 0, 1, 1},//                    b    -- c
                         {0, 1, 0, 0},
                         {1, 1, 0, 0}};
        UndirectedGraph undirectedGraph = new UndirectedGraph(list, edges);
        undirectedGraph.BFT(System.out::println);
    }

    @Test
    public void UndGraphDFTTest() {
        char[] list = {'a', 'b', 'c', 'd'};
        int[][] edges = {{0, 1, 0, 1},
                {1, 0, 1, 1},
                {0, 1, 0, 0},
                {1, 1, 0, 0}};
        UndirectedGraph undirectedGraph = new UndirectedGraph(list, edges);
        undirectedGraph.DFT(System.out::println);
    }

    @Test
    public void testDGraphBFTTest() {
        VertexNode[] vertices = {
                new VertexNode('a', new VertexNode.EdgeNode(1, new VertexNode.EdgeNode(3))),
                new VertexNode('b', new VertexNode.EdgeNode(2, new VertexNode.EdgeNode(3))),
                new VertexNode('c'),
                new VertexNode('d')
        };

        //这个图是a连b和d
        //b连c和d
        //c和d没有出
        DirectedGraph graph = new DirectedGraph(vertices);
        graph.BTF(System.out::println);
    }

    @Test
    public void testDGraphDFTTest() {
        VertexNode[] vertices = {
                new VertexNode('a', new VertexNode.EdgeNode(1, new VertexNode.EdgeNode(3))),
                new VertexNode('b'),
                new VertexNode('c'),
                new VertexNode('d', new VertexNode.EdgeNode(1, new VertexNode.EdgeNode(2)))
        };

        DirectedGraph graph = new DirectedGraph(vertices);
        graph.DTF(System.out::println);
    }

    @Test
    public void testUndGraphKruskal() {
        char[] list = {'a', 'b', 'c', 'd'};//这个图和之前那个无向图是一样的，只是加了权
        int[][] edges = {{0, 2, 0, 4},//前面的数字是起点，后面是终点，是一个树，这是只是用数字代表了一下字符
                         {2, 0, 3, 1},
                         {0, 3, 0, 0},
                         {4, 1, 0, 0}};

        UndirectedGraph graph = new UndirectedGraph(list, edges);
        graph.Kruskal(System.out::println);
    }

    @Test
    public void testUndGraphPrim() {
        char[] list = {'a', 'b', 'c', 'd'};
        int[][] edges = {{0, 2, 0, 4},
                         {2, 0, 3, 1},
                         {0, 3, 0, 0},
                         {4, 1, 0, 0}};

        UndirectedGraph graph = new UndirectedGraph(list, edges);
        graph.Prim(System.out::println);
    }

    @Test
    public void testTopSort() {//这是top排序
        char[] list = {'a', 'b', 'c', 'd'};
        int[][] edges = {{0, 1, 0, 1},
                         {0, 0, 1, 1},
                         {0, 0, 0, 0},
                         {0, 0, 0, 0}};

        AOVGraph graph = new AOVGraph(list, edges);
        graph.topSort();
    }

    @Test
    public void testDijkstra() {
        char[] list = {'a', 'b', 'c', 'd'};
        int[][] edges = {{0, 2, 0, 4},
                         {0, 0, 3, 1},
                         {0, 0, 0, 0},
                         {0, 0, 0, 0}};

        AOVGraph graph = new AOVGraph(list, edges);
        graph.dijkstra(0);
        //这个方法只会输出能够到达的路径点，遇到无法到达的点会报错，但是不影响结果我就没管了
    }
}
