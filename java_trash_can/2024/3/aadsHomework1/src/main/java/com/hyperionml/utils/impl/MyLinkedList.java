package com.hyperionml.utils.impl;

import com.hyperionml.pojo.Node;
import com.hyperionml.pojo.Student;
import com.hyperionml.utils.MyList;
import lombok.NoArgsConstructor;

@NoArgsConstructor
//这是链表的具体实现
public class MyLinkedList<T> implements MyList<T> {

    private Node<T> head;

    private int len = 0;

    public MyLinkedList(T val) {
        this.head = new Node<>(val);
    }

    public MyLinkedList(T[] elements) {
        this.head = new Node<>();
        Node<T> node = this.head;
        for (int i = 0; i < elements.length; i++) {
            node.val = elements[i];
            node.node = new Node<T>();
            node = node.node;
            len++;
        }
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int len() {
        return this.len;
    }

    @Override//新增
    public void add(T element) {
        Node<T> i = new Node<>(element);
        if(isEmpty()){
            this.head = i;
        }
        else {
            i.node = this.head;
            this.head = i;
        }
        len++;
    }

    @Override//根据元素找索引
    public int find(T element) {
        Node<T> node = this.head;
        for (int i = 0; i < len; i++) {
            if(node.val.equals(element)){
                return i;
            }
            node = node.node;
        }
        return -1;
    }

    @Override//输出表
    public void printList() {
        if(isEmpty()){
            System.out.println("空表");
        }
        Node<T> node = this.head;
        for (int i = 0; i < len - 1; i++) {
            System.out.println(node.val);
        }
    }

    @Override//根据索引查元素
    public T getElement(int index) throws Throwable {
        if(isEmpty()){
            throw new Throwable("空表");
        }
        if(index > len - 1 || index < 0){
            throw new Throwable("非法索引");
        }

        Node<T> node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.node;
        }
        return node.val;
    }

    @Override//插入
    public void insert(int index, T element) throws Throwable {
        if(index != 0 && isEmpty()){
            throw new Throwable("空表,非法索引");
        }
        if(index > len - 1 || index < 0){
            throw new Throwable("非法索引");
        }

        Node<T> node = this.head;
        for (int i = 0; i < index - 1; i++) {
            node = node.node;
        }
        Node<T> insert = new Node<>(element);
        insert.node = node.node;
        node.node = insert;
        len++;
    }

    @Override//删除
    public T delete(int index) throws Throwable {
        if(isEmpty()){
            throw new Throwable("空表");
        }
        if(index < 0 || index > len - 1){
            throw new Throwable("非法索引");
        }

        Node<T> node = this.head;
        Node<T> re;
        for (int i = 0; i < index - 1; i++) {
            node = node.node;
        }
        re = node.node;
        if(index == len - 1){
            node.node = null;
        }
        else {
            node.node = node.node.node;
        }
        return re.val;
    }


    @Override//清空
    public void clear() {
        this.head = null;
    }
}
