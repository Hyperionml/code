package com.hyperionml.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Iterator;

@NoArgsConstructor
public class LinkedListQueue<E> {

    @AllArgsConstructor
    private static class Node<E>{
        E value;
        Node<E> next;
    }

    private Node<E> head = new Node<>(null, null);
    private Node<E> tail = head;
    @Getter
    private int size; //大小(节点数)
    private int capacity = Integer.MAX_VALUE; //容量

    {
        tail.next = head;
    }

    public LinkedListQueue(int capacity) {
        this.capacity = capacity;
    }

    public boolean offer(E value) {
        if(isFull()){
            return false;
        }
        Node<E> added = new Node<>(value, head);
        tail.next = added;
        tail = tail.next;
        size++;
        return true;
    }

    public E poll() {
        if(isEmpty()){
            return null;
        }
        Node<E> first = head.next;
        head.next = first.next;
        size--;
        return first.value;
    }

    public E peek() {
        if(isEmpty()){
            return null;
        }
        return head.next.value;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public Iterator<E> iterator(){
        return new Iterator<>() {
            private Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != head;
            }

            @Override
            public E next() {
                E value = p.value;
                p = p.next;
                return value;
            }
        };
    }

}
