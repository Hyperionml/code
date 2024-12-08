package com.hyperionml.utils.impl;

import com.hyperionml.utils.MyList;

public class sqList<T> implements MyList<T> {
    private T[] list;
    private final static int LIST_SIZE = 100;
    private int len;

    public sqList() {
        list = (T[]) new Object[LIST_SIZE];
        len = 0;
    }

    public sqList(T[] initList, int len) throws Throwable {
        list = (T[]) new Object[LIST_SIZE];
        this.len = len;

        if(len > LIST_SIZE) throw new Throwable("长度越界");

        if (len >= 0) System.arraycopy(initList, 0, list, 0, len);
    }

    public boolean isEmpty(){
        return len == 0;
    }

    public int len(){
        return len;
    }

    @Override
    public void add(T element) {
        list[len] = element;
        len++;
    }

    public int find(T element){
        int re = 0;
        for (int i = 0; i < len; i++) {
            if(list[i].equals(element)){
                re = i + 1;
                break;
            }
        }
        return re;
    }

    public void printList(){
        if(isEmpty()) System.out.println("空表");
        for (int i = 0; i < len; i++) {
            System.out.println(list[i] + " ");
        }
    }

    public T getElement(int index) throws Throwable {
        if (isEmpty()) throw new Throwable("空表");

        if(index < 1 || index > len) throw new Throwable("非法索引");

        return list[index - 1];
    }

    public void insert(int index, T element) throws Throwable {
        if(len == list.length) throw new Throwable("表满，无法插入");

        if(index < 1 || index > len) throw new Throwable("非法索引");

        for (int i = len; i >= index ; i--) {
            list[index] = list[index - 1];
        }
        list[index-1] = element;
        len++;
    }

    public T delete(int index) throws Throwable {
        if (isEmpty()) throw new Throwable("空表");

        if(index < 1 || index > len) throw new Throwable("非法索引");

        T element = list[index-1];
        for (int i = index; i < len; i++) {
            list[i-1] = list[i];
        }
        len--;
        return element;
    }

    @Override
    public void clear() {
        this.list = (T[]) new Object[]{};
    }


}
