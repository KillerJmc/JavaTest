package com.test.algorithm.tree.impl;

import com.jmc.util.Binary;
import com.jmc.util.Pair;

import java.util.*;

public class HuffmanCode {
    /**
     * 节点类
     */
    private static record Node(Byte data, int weight, Node left, Node right) implements Comparable<Node> {
        public Node(byte data, int weight) {
            this(data, weight, null, null);
        }
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }

    /**
     * 获取所有节点
     * @param bs 字节数组
     * @return 所有节点的集合
     */
    private static List<Node> getNodes(byte[] bs) {
        var nodes = new ArrayList<Node>();

        // 统计字符
        var map = new HashMap<Byte, Integer>();
        for (byte b : bs) map.put(b, map.get(b) == null ? 1 : map.get(b) + 1);

        // 返回所有节点
        map.forEach((data, weight) -> nodes.add(new Node(data, weight)));
        return nodes;
    }

    /**
     * 创建霍夫曼树
     * @param nodes 所有节点的集合
     * @return 树的根节点
     */
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() != 1) {
            Collections.sort(nodes);
            Node left = nodes.get(0), right = nodes.get(1);
            Node parent = new Node(null, left.weight + right.weight, left, right);
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    /**
     * 获取霍夫曼编码
     * @param x 霍夫曼树的头节点
     * @param code 上一次调用确定的编码（左0右1）
     * @param sb 存放字符串
     * @param huffmanCodes 存放哈夫曼编码的集合
     */
    private static void getCode(Node x, String code, StringBuilder sb, Map<Byte, String> huffmanCodes) {
        var tmp = new StringBuilder(sb);
        tmp.append(code);

        if (x != null) {
            if (x.data == null) {
                getCode(x.left, "0", tmp, huffmanCodes);
                getCode(x.right, "1", tmp, huffmanCodes);
            } else {
                huffmanCodes.put(x.data, tmp.toString());
            }
        }
    }

    /**
     * 获取霍夫曼编码的简单方法
     * @param bs 字节数组
     * @return 存放哈夫曼编码的集合
     */
    private static Map<Byte, String> getCode(byte[] bs) {
        var huffmanCodes = new HashMap<Byte, String>();
        var nodes = getNodes(bs);
        var treeRoot = createHuffmanTree(nodes);
        getCode(treeRoot, "", new StringBuilder(), huffmanCodes);
        return huffmanCodes;
    }


    public static Pair<byte[], Map<Byte, String>> zip(byte[] bs) {
        var huffmanCodes = getCode(bs);
        var sb = new StringBuilder(bs.length);
        for (var b : bs) sb.append(huffmanCodes.get(b));

        byte[] result = new byte[(sb.length() + 7) / 8 + 1];
        int p = 0, i;
        for (i = 0; i + 8 < sb.length(); i += 8) result[p++] = Binary.toByte(sb.substring(i, i + 8), false);
        // 如果不能整除，就记录最后一个数二进制的位数，避免解压时候出错
        if (p == result.length - 2) {
            result[p] = Binary.toByte(sb.substring(i), false);
            result[result.length - 1] = (byte) sb.substring(i).length();
        } else {
            result[result.length - 1] = 8;
        }

        return new Pair<>(result, huffmanCodes);
    }

    public static byte[] unzip(Pair<byte[], Map<Byte, String>> pair) {
        byte[] bs = pair.getFirst();
        Map<Byte, String> huffmanCodes = pair.getSecond();

        var sb = new StringBuilder(bs.length * 2);
        for (int i = 0; i < bs.length - 2; i++) sb.append(Binary.toString(bs[i], false));
        // 处理最后一个数，找准二进制的位数，避免解压时候出错
        var last = Binary.toString(bs[bs.length - 2], false);
        sb.append(last.substring(8 - bs[bs.length - 1]));

        var map = new HashMap<String, Byte>();
        huffmanCodes.forEach((k, v) -> map.put(v, k));
        var list = new ArrayList<Byte>();

        for (int i = 0, p; i < sb.length(); i = p) {
            p = i + 1;
            Byte b;

            while ((b = map.get(sb.substring(i, p))) == null) p++;
            list.add(b);
        }
        byte[] result = new byte[list.size()];
        for (int i = 0; i < result.length; i++) result[i] = list.get(i);

        return result;
    }
}
