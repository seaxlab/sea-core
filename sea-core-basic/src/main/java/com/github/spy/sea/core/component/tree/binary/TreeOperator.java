package com.github.spy.sea.core.component.tree.binary;

import com.github.spy.sea.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树相关操作
 *
 * @author spy
 * @version 1.0 2021/5/6
 * @since 1.0
 */
@Slf4j
public final class TreeOperator {

    /**
     * 获取树节点深度
     *
     * @param node
     * @return
     */
    public static int treeDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(treeDepth(node.getLeft()), treeDepth(node.getRight())) + 1;
    }

    /**
     * 获取树节点叶子节点总数
     *
     * @param node
     * @return
     */
    public static int leafCount(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        }
        return leafCount(node.getLeft()) + leafCount(node.getRight());
    }

    /**
     * 层序遍历，层次遍历
     *
     * @param node 树的根
     */
    public static <T extends Comparable<? super T>> List<TreeNode<T>> levelOrder(TreeNode<T> node) {
        List<TreeNode<T>> data = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.pop();
            data.add(cur);
//            System.out.print(cur.getValue() + " ");
            if (cur.getLeft() != null) {
                queue.add(cur.getLeft());
            }
            if (cur.getRight() != null) {
                queue.add(cur.getRight());
            }
        }
        return data;
    }

    /**
     * 前序遍历，先遍历根
     *
     * @param node 树的根节点
     */
    public static <T extends Comparable<? super T>> List<TreeNode<T>> frontOrder(TreeNode<T> node) {
        if (node == null) {
            return ListUtil.empty();
        }
        List<TreeNode<T>> data = new ArrayList<>();
//        System.out.print(node.getValue() + " ");
        data.add(node);
        data.addAll(frontOrder(node.getLeft()));
        data.addAll(frontOrder(node.getRight()));
        return data;
    }

    /**
     * 中序遍历，中遍历根
     *
     * @param node 树的根节点
     */
    public static <T extends Comparable<? super T>> List<TreeNode<T>> middleOrder(TreeNode<T> node) {
        if (node == null) {
            return ListUtil.empty();
        }
        List<TreeNode<T>> data = new ArrayList<>();

        data.addAll(middleOrder(node.getLeft()));
        data.add(node);
//        System.out.print(node.getValue() + " ");
        data.addAll(middleOrder(node.getRight()));

        return data;
    }

    /**
     * 后序遍历，后遍历根
     *
     * @param node 树的根节点
     */
    public static <T extends Comparable<? super T>> List<TreeNode<T>> laterOrder(TreeNode<T> node) {
        if (node == null) {
            return ListUtil.empty();
        }
        List<TreeNode<T>> data = new ArrayList<>();
        data.addAll(laterOrder(node.getLeft()));
        data.addAll(laterOrder(node.getRight()));

        data.add(node);
//        System.out.print(node.getValue() + " ");

        return data;
    }
}
