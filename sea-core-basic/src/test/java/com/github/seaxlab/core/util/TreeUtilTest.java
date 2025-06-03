package com.github.seaxlab.core.util;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.util.model.TreeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/6/3
 * @since 1.0
 */
@Slf4j
public class TreeUtilTest {

  @Test
  public void testBuild() {
    List<Menu> menus = new ArrayList<>();
    menus.add(buildMenu("90", "85", 1L));
    menus.add(buildMenu("85", "81", 5L));
    menus.add(buildMenu("81", "8", 2L));

    log.info("{}", JSON.toJSONString(TreeUtil.build("8", menus), true));
  }


  private Menu buildMenu(String id, String parentId, Long sort) {
    Menu menu = new Menu();
    menu.setId(id);
    menu.setParentId(parentId);
    menu.setSort(sort);
    return menu;
  }


  @Data
  public static class Menu implements TreeService<Menu> {

    private String id;
    private String parentId;
    private Long sort;
    private List<Menu> children;


    @Override
    public String getId() {
      return this.id;
    }

    @Override
    public String getParentId() {
      return this.parentId;
    }

    @Override
    public Long getSort() {
      return this.sort;
    }

    @Override
    public List<Menu> getChildren() {
      return this.children;
    }

    @Override
    public void setChildren(List<Menu> children) {
      this.children = children;
    }
  }

}
