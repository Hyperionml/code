package com.hyperionml.pojo;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
public class Node<T> {
    public Node node;
    public T val;

    public Node(T val) {
        this.val = val;
    }
}
