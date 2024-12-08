package com.hyperionml.pojo;

/**
 * @author jin_run_jun
 * @date 2024/4/23 22:08
 */
public class TreeNode implements Comparable<TreeNode> {

    public Character data;
    public int weight;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(Character data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(TreeNode o) {
        return this.weight - o.weight;
    }

    public static void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root);
        preOrder(root.left);
        preOrder(root.right);
    }
}
