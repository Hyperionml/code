package com.hyperionml.utils;

public interface Queue<T> {

    boolean offer(T value);

    T poll();

    T peek();

    boolean isEmpty();

    boolean isFull();
}
