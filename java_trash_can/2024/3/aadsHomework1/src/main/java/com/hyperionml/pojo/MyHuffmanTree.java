package com.hyperionml.pojo;


import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.*;


public class MyHuffmanTree {

    @AllArgsConstructor
    @ToString
    private class Node {
        public char val;
        public int code;
        public Node left;
        public Node right;

        public Node(char val, int code) {
            this.val = val;
            this.code = code;
        }
    }

    private HashMap<Character, Integer> map;

    private Node tree = new Node('/', 1, new Node('l', 0),
            new Node('/', 1, new Node('o', 0),
                    new Node('/', 1, new Node('H', 0),
                            new Node('/', 1, new Node('w', 0),
                                    new Node('/', 1, new Node('e', 0),
                                            new Node('/', 1, new Node('d', 0),
                                                    new Node('/', 1, new Node('r', 0), null)))))));

    public void sum(String input){
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(map.containsKey(c) && !" ".equals(String.valueOf(c))){
                map.put(c, map.get(c) + 1);
            }else if(!map.containsKey(c) && !" ".equals(String.valueOf(c))) {
                map.put(c, 1);
            }
        }
        this.map = map;
        map.forEach((character, integer) -> System.out.println("在该语句中 " + character + " 共出现了 " + integer + " 次"));
    }

    public void createHuffmanTree() throws Throwable {
        if(map == null){
            throw new Throwable("请先执行map再执行该方法");
        }

        HashMap<Character, Integer> map = this.map;
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());

        list.sort(Map.Entry.comparingByValue());

        Node node = new Node('/', 1);
        Node head = node;
        for (Map.Entry<Character, Integer> entry : list) {
             node = new Node('/', 1, new Node(entry.getKey(), 0), node);
             //node = node.right;
        }

        this.tree = node;
        //System.out.println(node);
    }

    public String toCode(String input){
        StringBuilder re = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(String.valueOf(c).equals(" ")){
                continue;
            }
            Node node = this.tree;
            while (true){
                re.append(node.code);

                if(String.valueOf(c).equals(String.valueOf(node.left.val))){
                    re.append(node.left.code);
                    break;
                }
                node = node.right;
            }
        }
        return re.toString();
    }
}
