package com.hyperionml.utils;

import com.hyperionml.Person;
import com.hyperionml.pojo.TreeNode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Consumer;

public class AadsUtils {

    private AadsUtils() {
    }

    /*
         1、一元多项式的代数运算
            基本设计要求：
        （1）一元多项式要从键盘录入或从文件中读取系数、指数数据；
        （2）能够正确浏览各多项式；
        （3）计算任意两个一元多项式的加法、减法以及乘法，保留原多项式结果。
         */
    private static boolean isOperator(char ch){
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    private static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        if (op == '^') return 3;
        return 0;
    }

    private static double applyOp(double a, double b, char op) throws Throwable {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new Throwable("被除数不能为0");
                }
                yield  a / b;
            }
            case '^' -> Math.pow(a, b);
            default -> 0;
        };
    }

    public static double evaluateExpression(String expression) throws Throwable {
        LinkedList<Double> operandStack = new LinkedList<>();
        LinkedList<Character> operatorStack = new LinkedList<>();
        char[] exp = expression.toCharArray();
        for (int i = 0; i < exp.length; ++i) {
            char ch = exp[i];
            if (Character.isDigit(ch)) {
                // 处理多位数
                double operand = 0;
                while (i < exp.length && Character.isDigit(exp[i])) {
                    operand = operand * 10 + (exp[i] - '0');
                    ++i;
                }
                --i; // 因为for循环还会递增i，所以需要减回来
                operandStack.push(operand);
            } else if (ch == ' ') {
                // 忽略空格
                continue;
            } else if (isOperator(ch)) {
                // 处理操作符
                while (!operatorStack.isEmpty() && precedence(ch) <= precedence(operatorStack.getFirst())) {
                    double b = operandStack.poll();
                    double a = operandStack.poll();
                    char op = operatorStack.poll();
                    operandStack.push(applyOp(a, b, op));
                }
                operatorStack.push(ch);
            } else if (ch == '(') {
                // 处理左括号，压入操作符栈
                operatorStack.push(ch);
            } else if (ch == ')') {
                // 处理右括号
                while (!operatorStack.isEmpty() && operatorStack.getFirst() != '(') {
                    double b = operandStack.poll();
                    double a = operandStack.poll();
                    char op = operatorStack.poll();
                    operandStack.push(applyOp(a, b, op));
                }
                if (!operatorStack.isEmpty()) operatorStack.poll(); // 弹出左括号
            } else {
                // 如果字符不是数字、空格、操作符或括号，则忽略
                throw new Throwable("非法字符:" + ch);
            }
        }

        try {
            // 应用剩余的操作符
            while (!operatorStack.isEmpty()) {
                double b = operandStack.poll();
                double a = operandStack.poll();
                char op = operatorStack.poll();
                operandStack.push(applyOp(a, b, op));
            }
        } catch (Throwable e) {
            throw new Throwable("表达式非法");
        }

        // 栈顶元素就是结果
        return operandStack.getFirst();
    }


    /*
     2、算术表达式求值
        基本设计要求：
    （1）实现栈的基本操作；
    （2）将任意一个只有二元运算符的算术表达式转化成后缀表达式，并能够正确输出；
    （3）正确计算表达式的值。
    */
    public static List<String> convert(String infix) {
        Deque<Character> stack = new ArrayDeque<>();
        List<String> postfix = new ArrayList<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c)) {
                // 假设单个字符就是一个数字（实际情况可能需要处理多位数）
                postfix.add(String.valueOf(c));
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.add(String.valueOf(stack.pop()));
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // 弹出左括号，不添加到后缀表达式
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    postfix.add(String.valueOf(stack.pop()));
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.add(String.valueOf(stack.pop()));
        }

        return postfix;
    }

    /*
    3、舞伴问题
         基本设计要求：
    （1）实现队列的基本操作；
    （2）能够动态输入男伴和女伴人数；
    （3）每个舞曲进行一轮配对，每一轮配对要输出当次配对的各对舞伴，一轮结束
    （4）直到每一个人配对过至少三次后，结束跳舞。
    */
    public static void dancePartnerProblem(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("已进入舞伴问题模式");
        System.out.println("请输入男伴人数:");
        int menNum = scanner.nextInt();

        System.out.println("请输入女伴人数:");
        int wmNum = scanner.nextInt();

        int all = menNum + wmNum;

        LinkedList<Person> menQueue = new LinkedList<>();
        LinkedList<Person> wmQueue = new LinkedList<>();
        for (int i = 0; i < menNum; i++) {
            Person person = new Person();
            person.setName("men" + i);
            person.setIsMen(true);
            menQueue.add(person);
        }
        for (int i = 0; i < wmNum; i++) {
            Person person = new Person();
            person.setName("wm" + i);
            person.setIsMen(false);
            wmQueue.add(person);
        }

        int count = 1;
        while (true){
            System.out.println("当前为第" + count + "首曲子");
            System.out.println("配对如下：");
            int wmSize = wmQueue.size();
            int menSize = menQueue.size();
            for (int i = 0; i < Math.min(wmSize, menSize); i++) {
                Person wm = wmQueue.poll();
                Person men = menQueue.poll();
                System.out.println(wm.getName() + " " + men.getName());
                wm.setCompletedCount(wm.getCompletedCount() + 1);
                men.setCompletedCount(men.getCompletedCount() + 1);
                if(wm.getCompletedCount() < 3){
                    wmQueue.offer(wm);
                }
                if(men.getCompletedCount() < 3){
                    menQueue.offer(men);
                }
            }


            while (true){
                Scanner sc = new Scanner(System.in);
                System.out.println("当前曲目已结束，是否需要更新男女人数（输入1增加男伴人数，输入2增加女伴人数，输入con继续）");
                String input1 = sc.nextLine();
                if("1".equals(input1)){
                    System.out.println("请输入男伴增加人数：");
                    int newMenCount = sc.nextInt();
                    for (int i = 0; i < newMenCount; i++) {
                        Person person = new Person();
                        person.setName("men" + all);
                        person.setIsMen(true);
                        menQueue.offer(person);
                        all++;
                    }
                }
                else if("2".equals(input1)){
                    System.out.println("请输入女伴增加人数：");
                    int newWmCount = sc.nextInt();
                    for (int i = 0; i < newWmCount; i++) {
                        Person person = new Person();
                        person.setName("wm" + all);
                        person.setIsMen(false);
                        wmQueue.offer(person);
                        all++;
                    }
                }
                else if("con".equals(input1)){
                    break;
                }
                else {
                    System.out.println("指令不正确");
                }

            }

            System.out.println("你可以在此中断程序（输入exit）");
            String input2 = scanner.nextLine();
            if("exit".equals(input2)){
                break;
            }


            if(wmQueue.isEmpty() || menQueue.isEmpty()){
                System.out.println("其中一方已没有人了，舞池模式结束");
                break;
            }

            count++;
        }

    }


    /*
    4、哈夫曼编码与译码
    基本设计要求：
    （1）随意给出一段英文短文（内容不小于1个A4页）作为输入；
    （2）针对短文中各字符（包括标点符号）统计其出现的频率值；
    （3）根据统计出的频率值，自动生成01哈夫曼编码，并将该短文编码成01编码；
    （4）能够输出该编码表；
    （5）任意给出一组长度不小于1000位二进制编码，根据刚才所得的编码系统，进行翻译。
    */
    public static class HuffmanCode {
        /**
         * 将每个字符统计出现次数并转成节点加到list中
         *
         * @param str
         * @return
         */
        public static List<TreeNode> getNodes(String str) {
            //创建一个List
            ArrayList<TreeNode> nodes = new ArrayList<>();

            //遍历str，利用map,统计每一个字符出现的次数
            HashMap<Character, Integer> charCountMap = new HashMap<>();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (charCountMap.containsKey(c)) {
                    int count = charCountMap.get(c);
                    charCountMap.put(c, count + 1);
                } else {
                    charCountMap.put(c, 1);
                }
            }

            //把键值对转换成Node，加入到list中
            for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
                nodes.add(new TreeNode(entry.getKey(), entry.getValue()));
            }

            return nodes;
        }

        /**
         * 构建哈夫曼树
         *
         * @param nodes
         * @return
         */
        public static TreeNode creatHuffmanTree(List<TreeNode> nodes) {
            while (nodes.size() > 1) {
                //排序
                Collections.sort(nodes);
                //取出树中两个最小值
                TreeNode leftNode = nodes.get(0);
                TreeNode rightNode = nodes.get(1);
                //创建新节点
                TreeNode parent = new TreeNode(null, leftNode.weight + rightNode.weight);
                parent.left = leftNode;
                parent.right = rightNode;
                //删除已处理的节点
                nodes.remove(leftNode);
                nodes.remove(rightNode);
                //将新节点加入到list
                nodes.add(parent);
            }
            return nodes.get(0);
        }

        //生成赫夫曼树对应的赫夫曼编码
        // 将赫夫曼编码表存放在 Map
        static Map<Character, String> huffmanCodes = new HashMap<>();

        /**
         * 便于调用的入口
         *
         * @param root
         * @return
         */
        public static Map<Character, String> getCodes(TreeNode root) {
            StringBuilder stringBuilder = new StringBuilder();
            if (root == null) {
                return null;
            }
            //处理root的左子树
            getCodes(root.left, "0", stringBuilder);
            //处理root的右子树
            getCodes(root.right, "1", stringBuilder);
            return huffmanCodes;
        }

        /**
         * 得到字符对应的二进制编码（编码规则）
         *
         * @param node          传入结点
         * @param code          左0右1
         * @param stringBuilder 用于拼接路径
         */
        private static void getCodes(TreeNode node, String code, StringBuilder stringBuilder) {
            StringBuilder sb = new StringBuilder(stringBuilder);        //确保每次新的编码没有上次的残留
            sb.append(code);
            if (node != null) { //如果node == null不处理
                //判断当前node 是叶子结点还是非叶子结点
                if (node.data == null) { //非叶子结点
                    getCodes(node.left, "0", sb);
                    getCodes(node.right, "1", sb);
                } else {
                    huffmanCodes.put(node.data, sb.toString());
                }
            }
        }

        /**
         * 得到最终的哈夫曼编码
         *
         * @param str 待编译的字符串
         * @param map 编码规则
         * @return
         */
        private static String encodeString(String str, Map<Character, String> map) {
            StringBuilder sb = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (map.containsKey(c)) {
                    sb.append(map.get(c));
                } else {
                    throw new RuntimeException("字符不存在");
                }
            }
            return sb.toString();
        }

        /**
         * 得到二进制编码的方法入口
         *
         * @param str
         * @return
         */
        public static String encodeString(String str) {
            return encodeString(str, huffmanCodes);
        }

        /**
         * 解码（根据哈夫曼树解码）
         *
         * @param encodeString 待解码的二进制字符串
         * @param root         哈夫曼树
         * @return
         */
        public static String decode(String encodeString, TreeNode root) {
            StringBuilder sb = new StringBuilder();
            TreeNode cur = root;
            for (char c : encodeString.toCharArray()) {
                if (c == '0') {
                    cur = cur.left;
                } else if (c == '1') {
                    cur = cur.right;
                } else {
                    throw new RuntimeException("字符不存在");
                }
                if (cur.data != null) {
                    sb.append(cur.data);
                    cur = root;
                }
            }
            return sb.toString();
        }
    }


    /*
    5、校园导航问题
    基本设计要求：
    （1）设计你的校园平面图，至少包括10个以上的场所，每两个场所间可以有不同的路，且路长也可能不同；
    （2）能够正确输出该图中各边及权值；
    （3）找出从任意场所到达另一场所的最佳路径（最短路径）和路径长度。
    */
    @Getter
    @NoArgsConstructor
    public static class AOVGraph {
        private int num;
        private char[] list;
        private int[][] edges;

        public AOVGraph(char[] list, int[][] edges) {
            this.list = list;
            this.edges = edges;
            num = list.length;
        }

        public int getMin(int[] list){
            int r = Integer.MAX_VALUE;
            int re = -1;
            for (int i = 0; i < list.length; i++) {
                if(list[i] < r && list[i] != 0){
                    r = list[i];
                    re = i;
                }
            }
            return re;
        }

        //这个是最短路径算法，用的是迪杰斯特拉算法
        public void dijkstra(int startIndex){
            int[] dist = new int[num];
            String[] path = new String[num];
            int minEdge;
            for (int i = 0; i < num; i++) {
                dist[i] = edges[startIndex][i];
                if(dist[i] != -1){
                    path[i] = list[startIndex] + " " + list[i];
                }else {
                    path[i] = "";
                }
            }
            for (int num = 1; num < this.num; num++) {
                minEdge = getMin(dist);
                System.out.println("源点 " + list[startIndex] + " 到顶点 " + list[minEdge] + " 的最短路径为：" +
                        path[minEdge] + " " + dist[minEdge]);
                for (int i = 0; i < this.num; i++) {
                    if(edges[minEdge][i] != -1 && (dist[i] == -1 || dist[i] > dist[minEdge] + edges[minEdge][i])){
                        dist[i] = dist[minEdge] + edges[minEdge][i];
                        path[i] = path[minEdge] + " " + list[i];
                    }
                }
                dist[minEdge] = 0;
            }
        }
    }


    /*
    6、关键路径问题
    基本设计要求：
    （1）能够动态输入一个大型工程项目所构成的有向图；
    （2）能够动态输出该有向图中各边及权值；
    （3）正确求出其关键路径和关键活动。
    */
    public static class CriticalPath {

        /**
         * 顶点数组
         */
        @Getter
        private List<VertexNode> vexList;

        /**
         * etv:earliest time of vertex 事件的最早发生时间<p>
         * ltv:latest time of vertex 事件的最晚发生时间
         */
        private int[] etv;
        private int[] ltv;

        /**
         * 拓扑序列，保存各顶点拓扑排序的顺序
         */
        private Stack<Integer> stack2;


        /**
         * 创建图2（邻接表）
         */
        public void createGraph2() {
            //v0
            VertexNode v0 = new VertexNode(0, 0, null);

            EdgeNode v0e0 = new EdgeNode(2, 4, null);
            EdgeNode v0e1 = new EdgeNode(1, 3, null);

            v0.setFirstEdge(v0e0);
            v0e0.setNext(v0e1);

            //v1
            VertexNode v1 = new VertexNode(1, 1, null);

            EdgeNode v1e0 = new EdgeNode(4, 6, null);
            EdgeNode v1e1 = new EdgeNode(3, 5, null);

            v1.setFirstEdge(v1e0);
            v1e0.setNext(v1e1);

            //v2
            VertexNode v2 = new VertexNode(1, 2, null);

            EdgeNode v2e0 = new EdgeNode(5, 7, null);
            EdgeNode v2e1 = new EdgeNode(3, 8, null);

            v2.setFirstEdge(v2e0);
            v2e0.setNext(v2e1);

            //v3
            VertexNode v3 = new VertexNode(2, 3, null);

            EdgeNode v3e0 = new EdgeNode(4, 3, null);

            v3.setFirstEdge(v3e0);

            //v4
            VertexNode v4 = new VertexNode(2, 4, null);

            EdgeNode v4e0 = new EdgeNode(7, 4, null);
            EdgeNode v4e1 = new EdgeNode(6, 9, null);

            v4.setFirstEdge(v4e0);
            v4e0.setNext(v4e1);

            //v5
            VertexNode v5 = new VertexNode(1, 5, null);

            EdgeNode v5e0 = new EdgeNode(7, 6, null);

            v5.setFirstEdge(v5e0);

            //v6
            VertexNode v6 = new VertexNode(1, 6, null);

            EdgeNode v6e0 = new EdgeNode(9, 2, null);

            v6.setFirstEdge(v6e0);

            //v7
            VertexNode v7 = new VertexNode(2, 7, null);

            EdgeNode v7e0 = new EdgeNode(8, 5, null);

            v7.setFirstEdge(v7e0);

            //v8
            VertexNode v8 = new VertexNode(1, 8, null);

            EdgeNode v8e0 = new EdgeNode(9, 3, null);

            v8.setFirstEdge(v8e0);

            //v9
            VertexNode v9 = new VertexNode(2, 9, null);


            vexList = new ArrayList<>();
            vexList.add(v0);
            vexList.add(v1);
            vexList.add(v2);
            vexList.add(v3);
            vexList.add(v4);
            vexList.add(v5);
            vexList.add(v6);
            vexList.add(v7);
            vexList.add(v8);
            vexList.add(v9);
        }


        public boolean topologicalSort() {
            //统计输出顶点数
            int count = 0;

            //建栈存储入度为0的顶点
            Stack<Integer> stack = new Stack<>();

            //统计入度数
            for (int i = 0;i < vexList.size(); i++) {
                vexList.get(i).setIn(0);
            }
            for (int i = 0;i < vexList.size(); i++) {

                EdgeNode edge = vexList.get(i).getFirstEdge();
                while (edge != null) {
                    VertexNode vex = vexList.get(edge.getAdjvex());
                    vex.setIn(vex.getIn() + 1);

                    edge = edge.getNext();
                }
            }

            //将入度为0 的顶点入栈
            for (int i = 0;i < vexList.size(); i++) {
                if (vexList.get(i).getIn() == 0) {
                    stack.push(i);
                }
            }

            //----新加begin---- 初始化
            etv = new int[vexList.size()];
            stack2 = new Stack<>();
            //----新加end----

            //System.out.print("拓扑序列：");
            while (!stack.isEmpty()) {
                //栈顶 顶点出栈
                int vexIndex = stack.pop();
                //System.out.print(vexIndex + "  ");

                count++;

                //----新加 。将弹出的顶点序号压入拓扑序列的栈
                stack2.push(vexIndex);

                EdgeNode edge = null;
                //----循环方式变了一下
                for (edge = vexList.get(vexIndex).getFirstEdge(); edge != null; edge = edge.getNext()){

                    int adjvex = edge.getAdjvex();

                    VertexNode vex = vexList.get(adjvex);

                    //将此 顶点的入度减一
                    vex.setIn(vex.getIn() - 1);
                    //此顶点的入度为零则入栈，以便于下次循环输出
                    if (vex.getIn() == 0) {
                        stack.push(adjvex);
                    }

                    //----新加 求各顶点的最早发生时间值。
                    if (etv[vexIndex] + edge.getWeight() > etv[adjvex]) {
                        etv[adjvex] = etv[vexIndex] + edge.getWeight();
                    }
                }
            }

            return count == vexList.size();
        }

        public void criticalPath() {
            //求拓扑序列，计算数组etv和stack2的值
            boolean success = topologicalSort();
            if (!success) {
                System.out.println("\n有回路");
                return;
            }

            //声明活动最早发生时间和最迟发生时间
            int ete, lte;

            //初始化ltv
            ltv = new int[vexList.size()];
            for (int i = 0; i <vexList.size(); i++)
                ltv[i] = etv[vexList.size() - 1];

            System.out.print("\n关键路径:\n");
            //求顶点的最晚发生时间
            while (!stack2.isEmpty()) {
                //将拓扑序列出栈
                int vexIndex = stack2.pop();

                EdgeNode edge = null;
                for (edge = vexList.get(vexIndex).getFirstEdge();
                     edge != null; edge = edge.getNext()) {
                    int adjvex = edge.getAdjvex();

                    //求各顶点最晚发生时间
                    //已知最早发生时间，才能求最晚发生时间，顺序不能倒过来
                    //最晚完成时间要按拓扑序列逆推出来
                    //个人理解：求最晚和最早原理相同，只不过是返回来
                    if (ltv[adjvex] - edge.getWeight() < ltv[vexIndex]) {
                        ltv[vexIndex] = ltv[adjvex] - edge.getWeight();
                    }
                }
            }

            for (int i = 0; i < vexList.size(); i++) {
                EdgeNode edge = null;
                for (edge = vexList.get(i).getFirstEdge(); edge != null; edge = edge.getNext()) {
                    int adjvex = edge.getAdjvex();

                    //活动最早发生时间，即为边的弧头的最早发生时间
                    ete = etv[i];
                    //活动最晚发生时间，即为弧尾的的最晚发生时间减去权值
                    lte = ltv[adjvex] - edge.getWeight();
                    //相等即为关键路径
                    if (ete == lte) {
                        System.out.printf("<v%d,v%d>: %d\n",
                                vexList.get(i).getData(), vexList.get(adjvex).getData(), edge.getWeight());
                    }
                }
            }
        }

        public class EdgeNode {
            private int adjvex;
            private int weight;
            private EdgeNode next;



            public EdgeNode(int adjvex, int weight, EdgeNode next) {
                super();
                this.adjvex = adjvex;
                this.weight = weight;
                this.next = next;
            }

            public int getAdjvex() {
                return adjvex;
            }

            public void setAdjvex(int adjvex) {
                this.adjvex = adjvex;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public EdgeNode getNext() {
                return next;
            }

            public void setNext(EdgeNode next) {
                this.next = next;
            }


        }

        public class VertexNode {
            private int in;
            private int data;
            private EdgeNode firstEdge;



            public VertexNode(int in, int data, EdgeNode firstEdge) {
                super();
                this.in = in;
                this.data = data;
                this.firstEdge = firstEdge;
            }

            public int getIn() {
                return in;
            }

            public void setIn(int in) {
                this.in = in;
            }

            public int getData() {
                return data;
            }

            public void setData(int data) {
                this.data = data;
            }

            public EdgeNode getFirstEdge() {
                return firstEdge;
            }

            public void setFirstEdge(EdgeNode firstEdge) {
                this.firstEdge = firstEdge;
            }


        }

    }


    /*
    7、最小生成树问题
    基本设计要求：
    （1）设计一个无向网，该网中包含n个城市，在n个城市之间建设网络，每对城市之间要规定其建设网络的造价；
    （2）能够动态输入和输出该城市图；
    （3）在只需保证连通的条件下，求最经济的架设方法。
    */
    public static class UndirectedGraph {
        //这是无向图，是邻接矩阵的实现方式
        private int num;
        private char[] list;

        @Getter
        private int[][] edges;

        public UndirectedGraph(char[] list, int[][] edges) {
            this.num = list.length;
            this.list = list;
            this.edges = edges;
        }

        public void Kruskal(Consumer<Integer> action){
            final int[] edgeNum = {0};
            int[][] arr = new int[num][num];
            Map<Integer, Integer> edgeMap = new HashMap<>();
            for (int i = 0; i < num; i++) {
                for (int j = 0; j <= i; j++) {
                    if(i == j || edges[i][j] == 0){
                        continue;
                    }
                    edgeMap.put(edges[i][j], (i + 1) + (j + 1) * 10);
                }
            }
            edgeMap.forEach((integer, integer2) -> {
                if(arr[integer2/10 - 1][integer2%10 - 1] == 0){
                    if(edgeNum[0] < num -1){
                        action.accept(integer2);
                    }
                    edgeNum[0]++;
                    arr[integer2/10 - 1][integer2%10 - 1] = 1;
                    for (int i = 0; i < num; i++) {
                        if(arr[integer2/10 -1][i] == 1){
                            arr[integer2%10 -1][i] = 1;
                        }
                    }
                }
            });
        }


    }

}
