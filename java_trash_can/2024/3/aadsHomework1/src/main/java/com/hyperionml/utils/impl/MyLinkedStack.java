package com.hyperionml.utils.impl;

import com.hyperionml.pojo.Node;

public class MyLinkedStack<T> {
    private Node<T> top = new Node<>();

    public T getTop() {
        return (T) top.node.val;
    }

    public T pull() throws Throwable {
        if(top.node == null){
            throw new Throwable("空栈");
        }
        
        Node<T> re = this.top.node;
        if(top.node.node != null){
            top.node = top.node.node;
        }
        else {
            top.node = null;
        }
        return re.val;
    }
    
    public void push(T val){
        Node<T> newNode = new Node<>(val);
        newNode.node = top.node;
        top.node = newNode;
    }

    public void showAll(){
        Node<T> n = top.node;
        while (n != null){
            System.out.println(n.val);
            n = n.node;
        }
    }

    public boolean isEmpty() {
        return top.node == null || top.node.val == null;
    }
}
