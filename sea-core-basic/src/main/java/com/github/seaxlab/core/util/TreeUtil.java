package com.github.seaxlab.core.util;

import com.github.seaxlab.core.util.model.TreeService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * tree util
 *
 * @author spy
 * @version 1.0 2025/6/3
 * @since 1.0
 */
public final class TreeUtil {

  /**
   * List转换成树
   * @param parentId 父级ID
   * @param treeList
   * @return
   */
  public static <T extends TreeService> List<T> build(String parentId, List<T> treeList) {
    Map<String, List<T>> map = treeList.stream().collect(Collectors.groupingBy(TreeService::getParentId));
    //
    return build(map.get(parentId), map, Comparator.comparingLong(TreeService::getSort), null, null);
  }


  /**
   * List转换成树
   * @param parentId 父级ID
   * @param treeList
   * @return
   */
  public static <T extends TreeService> List<T> build(String parentId, List<T> treeList, //
                                                      Comparator<? super T> sortedComparator) {
    Map<String, List<T>> map = treeList.stream().collect(Collectors.groupingBy(TreeService::getParentId));
    //
    return build(map.get(parentId), map, sortedComparator, null, null);
  }


  /**
   * List转换成树
   * @param parentId 父级ID
   * @param treeList
   * @return
   */
  public static <T extends TreeService> List<T> build(String parentId, List<T> treeList, Comparator<? super T> sortedComparator, //
                                                      Consumer<? super T> doBefore, Function<? super T, T> doAfter) {
    Map<String, List<T>> map = treeList.stream().collect(Collectors.groupingBy(TreeService::getParentId));
    return build(map.get(parentId), map, sortedComparator, doBefore, doAfter);
  }

  public static <T extends TreeService> List<T> build(List<T> treeList, Map<String, List<T>> parentIdMap, //
                                                      Comparator<? super T> sortedComparator, //
                                                      Consumer<? super T> doBefore, Function<? super T, T> doAfter) {

    List<T> list = new ArrayList<>();
    Optional.ofNullable(treeList).orElse(new ArrayList<>())
            // 递归，最末的父节点从整个列表筛选出它的子节点列表依次组装
            .forEach(tree -> {
              if (doBefore != null) {
                doBefore.accept(tree);
              }
              if (CollectionUtils.isEmpty(tree.getChildren())) {
                List<T> children = build(parentIdMap.get(tree.getId()), parentIdMap, sortedComparator, doBefore, doAfter);
                tree.setChildren(children);
              }
              if (doAfter != null) {
                tree = doAfter.apply(tree);
              }
              if (tree != null) {
                list.add(tree);
              }
            });
    //
    if (!CollectionUtils.isEmpty(list)) {
      list.sort(sortedComparator);
    }
    return list;
  }


  /**
   * 将树状结构转换为平铺数据结构(将含有children的树状结构转换为仅有节点和父节点的对象列表)
   * @param source 树状结构数据列表
   * @param <T> 任意类型参数
   * @return 平铺数据结构列表
   */
  public static <T extends TreeService> List<T> convertToObjList(List<T> source) {
    List<T> outList = new ArrayList<>();
    convertToObjList(source, outList);
    return outList;
  }


  /**
   * 将树状结构转换为平铺数据结构(将含有children的树状结构转换为仅有节点和父节点的对象列表)
   * @param source 树状结构数据列表
   * @param outList 平铺数据结构列表
   * @param <T> 任意类型参数
   */
  public static <T extends TreeService> void convertToObjList(List<T> source, List<T> outList) {
    if (outList == null) {
      outList = new ArrayList<>();
    }
    for (T tTreeStructure : source) {
      if (tTreeStructure.getChildren() != null) {
        outList.add(tTreeStructure);
        //
        convertToObjList(tTreeStructure.getChildren(), outList);
      } else {
        outList.add(tTreeStructure);
      }
    }
  }


}
