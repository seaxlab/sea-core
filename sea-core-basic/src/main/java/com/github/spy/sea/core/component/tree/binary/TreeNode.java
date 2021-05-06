package com.github.spy.sea.core.component.tree.binary;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 二叉树 节点
 *
 * @author spy
 * @version 1.0 2021/5/6
 * @since 1.0
 */
@Slf4j
public class TreeNode<T extends Comparable<? super T>> {

    // 节点唯一标识，同一棵树中不能重复
    private String key;

    private T value;

    private TreeNode left;

    private TreeNode right;

    public TreeNode(T value) {
        this.value = value;
    }

    public TreeNode(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeNode)) return false;
        TreeNode<?> treeNode = (TreeNode<?>) o;
        return Objects.equals(key, treeNode.key) && Objects.equals(getValue(), treeNode.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, getValue());
    }
}
