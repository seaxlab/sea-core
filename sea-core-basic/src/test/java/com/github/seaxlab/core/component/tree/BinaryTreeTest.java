package com.github.seaxlab.core.component.tree;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.tree.binary.TreeNode;
import com.github.seaxlab.core.component.tree.binary.TreeOperator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/6
 * @since 1.0
 */
@Slf4j
public class BinaryTreeTest extends BaseCoreTest {
  private TreeNode<Character> treeNode;

  @Before
  public void setUp() {
    /**
     *   树节点      A
     *        B             C
     *   D        E     F        G
     *         H
     *
     */
    treeNode = new TreeNode<>('A');
    treeNode.setLeft(new TreeNode<>('B'));
    treeNode.getLeft().setLeft(new TreeNode<>('D'));
    treeNode.getLeft().setRight(new TreeNode<>('E'));
    treeNode.getLeft().getRight().setLeft(new TreeNode<>('H'));
    treeNode.setRight(new TreeNode<>('C'));
    treeNode.getRight().setLeft(new TreeNode<>('F'));
    treeNode.getRight().setRight(new TreeNode<>('G'));
  }

  @Test
  public void levelOrder() throws Exception {
    log.info("{}", printTreeValue(TreeOperator.levelOrder(treeNode)));
  }

  @Test
  public void frontOrder() throws Exception {
    //
    log.info("{}", printTreeValue(TreeOperator.frontOrder(treeNode)));
  }

  @Test
  public void middleOrder() throws Exception {
    //D B H E A F C G
    log.info("{}", printTreeValue(TreeOperator.middleOrder(treeNode)));
  }

  @Test
  public void laterOrder() throws Exception {
    //D H E B F G C A
    log.info("{}", printTreeValue(TreeOperator.laterOrder(treeNode)));
  }

  @Test
  public void treeDepth() {
    int depth = TreeOperator.treeDepth(treeNode);
    System.out.println(depth);
    Assert.assertEquals(depth, 4);
  }

  @Test
  public void leafCount() {
    int count = TreeOperator.leafCount(treeNode);
    System.out.println(count);
    Assert.assertEquals(count, 4);
  }

  private List<Character> printTreeValue(List<TreeNode<Character>> nodes) {
    return nodes.stream().map(TreeNode::getValue).collect(Collectors.toList());
  }
}
