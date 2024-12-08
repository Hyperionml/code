package com.hyperionml.utils.impl;

import java.util.Arrays;

public class SortUtils {

    private SortUtils() {
    }

    public static void selectionSort(int[] arr){
        System.out.println("简单选择排序");
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            int min = Integer.MAX_VALUE, minIndex = i;
            for (int j = i; j < arr.length; j++) {
                if(arr[j] < min){
                    min = arr[j];
                    minIndex = j;
                }
            }
            int a = arr[i];
            arr[i] = min;
            arr[minIndex] = a;
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void bubbleSort(int[] arr){
        System.out.println("冒泡排序");
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if(arr[j] > arr[j + 1]){
                    int a = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = a;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    //希尔排序
    public static void shellSort(int[] arr){
        System.out.println("希尔排序");
        System.out.println(Arrays.toString(arr));
        int temp, position;
        for (int d = arr.length / 2; d >= 1; d = d/2) {
            for (int i = d; i < arr.length; i++) {
                temp = arr[i];
                for (position = i; position - d >= 0 && temp < arr[position-d]; position = position - d){
                    arr[position] = arr[position - d];
                }
                arr[position] = temp;
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    //快速排序
    public static void quickSort(int first, int end, int[] arr){
        if(first >= end) {
            return;
        }
        else {
            int i = first, j = end, temp;
            while (i<j){
                while (i<j && arr[i] <= arr[j]){
                    j--;
                }
                if(i < j){
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    i++;
                }
                while (i<j && arr[i] <= arr[j]){
                    i++;
                }
                if(i < j){
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    j--;
                }
            }
            int pivot = i;
            quickSort(first, pivot-1, arr);
            quickSort(pivot + 1, end, arr);
        }
        System.out.println(Arrays.toString(arr));
    }


    //直接插入排序
    public static void insertionSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if(arr[i] < arr[j]){
                    int temp = arr[i];
                    System.arraycopy(arr, j, arr, j + 1, i - j);
                    arr[j] = temp;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    //堆排序
    public static void heapSort(int[] arr){
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
            System.out.println(Arrays.toString(arr));
        }
    }

    private static void heapify(int[] arr, int n, int node){
        int left = 2 * node + 1;
        int right = 2 * node + 2;
        int largest = node;

        if(left < n && arr[left] > arr[largest]){
            largest = left;
        }

        if(right < n && arr[right] > arr[largest]){
            largest = right;
        }

        if(largest != node){
            int temp = arr[node];
            arr[node] = arr[largest];
            arr[largest] = temp;

            heapify(arr, n, largest);
        }
    }
}
