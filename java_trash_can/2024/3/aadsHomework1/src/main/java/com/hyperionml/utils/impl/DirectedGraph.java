package com.hyperionml.utils.impl;

import com.hyperionml.pojo.VertexNode;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class DirectedGraph {
//这是有向图,是邻接表的实现
    private VertexNode[] vertices;
    private int num;

    public DirectedGraph(VertexNode[] vertices) {
        this.vertices = vertices;
        this.num = vertices.length;
    }

    public void BTF(Consumer<Character> action){
        int[] arr = new int[num];
        action.accept(vertices[0].getVertex());
        for (VertexNode vertex : vertices) {
            VertexNode.EdgeNode node = vertex.getFirst();
            while (node != null) {
                if (arr[node.getAdjvex()] == 0) {
                    action.accept(vertices[node.getAdjvex()].getVertex());
                    arr[node.getAdjvex()] = 1;
                }
                node = node.getNext();
            }
        }
    }

    public void DTF(Consumer<Character> action){
        int[] arr = new int[num];
        Stack<VertexNode> stack = new Stack<>();
        stack.push(vertices[0]);
        VertexNode node;
        while (!stack.isEmpty()){
            node = stack.pop();
            if(arr[List.of(vertices).indexOf(node)] == 0){
                action.accept(node.getVertex());
                arr[List.of(vertices).indexOf(node)] = 1;
                VertexNode.EdgeNode edgeNode = node.getFirst();
                while (edgeNode != null){
                    stack.push(vertices[edgeNode.getAdjvex()]);
                    edgeNode = edgeNode.getNext();
                }
            }
        }

    }

}
