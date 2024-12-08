package com.hyperionml.utils.impl;

import lombok.*;

import java.util.Stack;

@Getter
@NoArgsConstructor
public class AOVGraph {
    private int num;
    private char[] list;
    private int[][] edges;

    public AOVGraph(char[] list, int[][] edges) {
        this.list = list;
        this.edges = edges;
        num = list.length;
    }

    //拓扑排序
    public void topSort(){
        int count = 0;
        int[] arr = new int[num];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < num; i++) {
            if(getInDegree(i) == 0){
                stack.push(i);
                arr[i] = 1;
            }
        }
        System.out.println("拓扑排序为：");
        while (!stack.isEmpty()){
            int index = stack.pop();
            System.out.println(list[index]);
            count++;

            for (int i = 0; i < num; i++) {
                if(edges[index][i] != 0){
                    edges[index][i] = 0;
                }
                if(getInDegree(i) == 0 && arr[i] != 1){
                    stack.push(i);
                    arr[i] = 1;
                }
            }
        }
        if (count < num){
            System.out.println("有回路");
        }
    }

    private int getInDegree(int i){
        int re = 0;
        for (int j = 0; j < num; j++) {
            if(edges[j][i] == 1){
                re++;
            }
        }
        return re;
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
