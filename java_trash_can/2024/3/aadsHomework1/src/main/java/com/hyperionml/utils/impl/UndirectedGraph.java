package com.hyperionml.utils.impl;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UndirectedGraph {
//这是无向图，是邻接矩阵的实现方式
    private int num;
    private char[] list;
    private int[][] edges;

    public UndirectedGraph(char[] list, int[][] edges) {
        this.num = list.length;
        this.list = list;
        this.edges = edges;
    }

    //广度优先
    public void BFT(Consumer<Character> consumer){
        int[] arr = new int[num];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        arr[0] = 1;
        while (!queue.isEmpty()){
            int index = queue.poll();
            consumer.accept(list[index]);
            for (int i = 0; i < num; i++) {
                if(edges[index][i] == 1 && arr[i] == 0){
                    queue.offer(i);
                    arr[i] = 1;
                }
            }
        }
    }

    //深度优先
    public void DFT(Consumer<Character> consumer){
        int[] arr = new int[num];
        Stack<Integer> st = new Stack<>();
        st.push(0);
        arr[0] = 1;
        while (!st.isEmpty()){
            int index = st.pop();
            consumer.accept(list[index]);
            for (int i = 0; i < num; i++){
                if(edges[index][i] == 1 && arr[i] == 0){
                    st.push(i);
                    arr[i] = 1;
                }
            }
        }
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

    public void Prim(Consumer<String> action){
        boolean[] selected = new boolean[num];
        int[] minDist = new int[num];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        int[] parent = new int[num];
        Arrays.fill(parent, -1);

        int targetIndex = 0;
        selected[targetIndex] = true;
        minDist[targetIndex] = 0;

        while (contains(selected, false)){

            // update，更新连接边的顶点的列表
            for (int i = 0; i < num; i++) {
                if(edges[targetIndex][i] != 0 && edges[targetIndex][i] < minDist[i]){
                    minDist[i] = edges[targetIndex][i];
                    parent[i] = targetIndex;
                }
            }

            // scan，扫描还没遍历的节点中边最小的节点
            int minEdgeIndex = 0;
            int minEdge = Integer.MAX_VALUE;
            for (int i = 0; i < minDist.length; i++) {
                if(minDist[i] == 0){
                    continue;
                }
                if(minDist[i] < minEdge){
                    minEdgeIndex = i;
                    minEdge = minDist[i];
                }
            }

            // add,这一步是将得到的边的终点在遍历列表中做标记
            selected[minEdgeIndex] = true;
            minDist[minEdgeIndex] = 0;

            //parent[minEdgeIndex]表示的是这条边的父节点，minEdgeIndex表示这条边的子节点
            action.accept(list[parent[minEdgeIndex]] + String.valueOf(list[minEdgeIndex]));

            targetIndex = minEdgeIndex;


        }

    }

    public static boolean contains(boolean[] array, boolean element) {
        for (boolean b : array) {
            if (b == element) {
                return true;
            }
        }
        return false;
    }

    private static int getMin(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        int minIndex = 0;
        if(array[minIndex] == 0){
            array[minIndex] = Integer.MAX_VALUE;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[minIndex] && array[i] != 0) {
                minIndex = i;
            }
        }
        return minIndex;
    }

}
