package com.hyperionml.pojo;

import com.hyperionml.utils.impl.MyLinkedStack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class TreeNode {
    public char val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(char val) {
        this.val = val;
    }

    public TreeNode( TreeNode left, char val, TreeNode right) {
        this.left = left;
        this.val = val;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

    public void iterator1(Consumer<Character> consumer) throws Throwable {
        MyLinkedStack<TreeNode> stack = new MyLinkedStack<>();

        TreeNode curr = this;
        while (curr != null || !stack.isEmpty()){
            if(curr != null){
                //System.out.println("去:" + curr.val);//前序遍历位置
                consumer.accept(curr.val);
                stack.push(curr);
                curr = curr.left;
            }
            else {
                TreeNode pull = stack.pull();
                curr = pull.right;
            }
        }

    }

    //中序遍历
    public void iterator2(Consumer<Character> consumer) throws Throwable {
        MyLinkedStack<TreeNode> stack = new MyLinkedStack<>();

        TreeNode curr = this;
        while (curr != null || !stack.isEmpty()){
            if(curr != null){
                stack.push(curr);
                curr = curr.left;
            }
            else {
                TreeNode pull = stack.pull();
                consumer.accept(pull.val);//这里是操作每一个遍历元素的位置
                curr = pull.right;
            }
        }

    }

    public void iterator3(Consumer<Character> consumer) throws Throwable {
        MyLinkedStack<TreeNode> stack = new MyLinkedStack<>();

        TreeNode curr = this;
        TreeNode pull = null;
        while (curr != null || !stack.isEmpty()){
            if(curr != null){
                stack.push(curr);
                curr = curr.left;
            }
            else {
                TreeNode peek = stack.getTop(); // 栈顶元素
                if(peek.right == null || peek.right == pull){ //右子树处理完成
                    pull = stack.pull();
                    consumer.accept(pull.val);
                } else {
                    curr = peek.right;
                }
            }
        }
    }

    public int getDepth(){
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode curr = this;
        int depth = 0;
        queue.offer(curr);
        while (!queue.isEmpty()){
            depth++;
            Queue<TreeNode> queue1 = new LinkedList<>();
            while (!queue.isEmpty()){
                TreeNode poll = queue.poll();
                if(poll.left != null){
                    queue1.offer(poll.left);
                }
                if(poll.right != null){
                    queue1.offer(poll.right);
                }
            }
            queue = queue1;
        }
        return depth;
    }

    public int getLeafCount(){
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode curr = this;
        int count = 0;
        queue.offer(curr);
        while (!queue.isEmpty()){
            Queue<TreeNode> queue1 = new LinkedList<>();
            while (!queue.isEmpty()){
                TreeNode poll = queue.poll();
                if(poll.left == null && poll.right == null){
                    count++;
                }
                if(poll.left != null){
                    queue1.offer(poll.left);
                }
                if(poll.right != null){
                    queue1.offer(poll.right);
                }
            }
            queue = queue1;
        }
        return count;
    }

    public int getNodeLevel(char val){
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode curr = this;
        int level = 0;
        queue.offer(curr);
        while (!queue.isEmpty()){
            level++;
            Queue<TreeNode> queue1 = new LinkedList<>();
            while (!queue.isEmpty()){
                TreeNode poll = queue.poll();
                if(Character.valueOf(poll.val).equals(val)){
                    return level;
                }
                if(poll.left != null){
                    queue1.offer(poll.left);
                }
                if(poll.right != null){
                    queue1.offer(poll.right);
                }
            }
            queue = queue1;
        }
        return -1;
    }
}
