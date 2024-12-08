package com.jrj;

import java.util.*;

/**
 * @author jin_run_jun
 * @date 2024/4/23 22:11
 */
public class HuffmanCode {
    /**
     * 将每个字符统计出现次数并转成节点加到list中
     *
     * @param str
     * @return
     */
    public static List<TreeNode> getNodes(String str) {
        //创建一个List
        ArrayList<TreeNode> nodes = new ArrayList<>();

        //遍历str，利用map,统计每一个字符出现的次数
        HashMap<Character, Integer> charCountMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (charCountMap.containsKey(c)) {
                int count = charCountMap.get(c);
                charCountMap.put(c, count + 1);
            } else {
                charCountMap.put(c, 1);
            }
        }

        //把键值对转换成Node，加入到list中
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            nodes.add(new TreeNode(entry.getKey(), entry.getValue()));
        }

        return nodes;
    }

    /**
     * 构建哈夫曼树
     *
     * @param nodes
     * @return
     */
    public static TreeNode creatHuffmanTree(List<TreeNode> nodes) {
        while (nodes.size() > 1) {
            //排序
            Collections.sort(nodes);
            //取出树中两个最小值
            TreeNode leftNode = nodes.get(0);
            TreeNode rightNode = nodes.get(1);
            //创建新节点
            TreeNode parent = new TreeNode(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            //删除已处理的节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新节点加入到list
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    //生成赫夫曼树对应的赫夫曼编码
    // 将赫夫曼编码表存放在 Map
    static Map<Character, String> huffmanCodes = new HashMap<>();

    /**
     * 便于调用的入口
     *
     * @param root
     * @return
     */
    public static Map<Character, String> getCodes(TreeNode root) {
        StringBuilder stringBuilder = new StringBuilder();
        if (root == null) {
            return null;
        }
        //处理root的左子树
        getCodes(root.left, "0", stringBuilder);
        //处理root的右子树
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }

    /**
     * 得到字符对应的二进制编码（编码规则）
     *
     * @param node          传入结点
     * @param code          左0右1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(TreeNode node, String code, StringBuilder stringBuilder) {
        StringBuilder sb = new StringBuilder(stringBuilder);        //确保每次新的编码没有上次的残留
        sb.append(code);
        if (node != null) { //如果node == null不处理
            //判断当前node 是叶子结点还是非叶子结点
            if (node.data == null) { //非叶子结点
                getCodes(node.left, "0", sb);
                getCodes(node.right, "1", sb);
            } else {
                huffmanCodes.put(node.data, sb.toString());
            }
        }
    }

    /**
     * 得到最终的哈夫曼编码
     *
     * @param str 待编译的字符串
     * @param map 编码规则
     * @return
     */
    private static String encodeString(String str, Map<Character, String> map) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) {
                sb.append(map.get(c));
            } else {
                throw new RuntimeException("字符不存在");
            }
        }
        return sb.toString();
    }

    /**
     * 得到二进制编码的方法入口
     *
     * @param str
     * @return
     */
    public static String encodeString(String str) {
        return encodeString(str, huffmanCodes);
    }

    /**
     * 解码（根据哈夫曼树解码）
     *
     * @param encodeString 待解码的二进制字符串
     * @param root         哈夫曼树
     * @return
     */
    public static String decode(String encodeString, TreeNode root) {
        StringBuilder sb = new StringBuilder();
        TreeNode cur = root;
        for (char c : encodeString.toCharArray()) {
            if (c == '0') {
                cur = cur.left;
            } else if (c == '1') {
                cur = cur.right;
            } else {
                throw new RuntimeException("字符不存在");
            }
            if (cur.data != null) {
                sb.append(cur.data);
                cur = root;
            }
        }
        return sb.toString();
    }
}
