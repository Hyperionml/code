package com.hyperionml.utils;

public interface MyList<T> {

    boolean isEmpty();

    int len();

    void add(T element);

    int find(T element);

    void printList();

    T getElement(int index) throws Throwable;

    void insert(int index, T element) throws Throwable;

    T delete(int index) throws Throwable;

    void clear();
}
