package com.hyperionml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VertexNode {
    private char vertex;
    private EdgeNode first;

    public VertexNode(char vertex) {
        this.vertex = vertex;
    }


    @Data
    @AllArgsConstructor
    public static class EdgeNode{
        private int adjvex;
        private EdgeNode next;

        public EdgeNode(int adjvex) {
            this.adjvex = adjvex;
        }

    }


}
