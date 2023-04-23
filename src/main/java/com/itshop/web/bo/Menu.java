package com.itshop.web.bo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单
 *
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
//    @JsonIgnore
//    @JSONField(serialize=false)
    private Integer id;
    /**
     * 父id
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer parentId;

    /**
     * 层级
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer level;

    /**
     * 有层级结构的key
     */
    private String key;

    /**
     * 标识
     */
    private String code;
    /**
     * 名称
     */
    @JSONField(name = "title")
    private String text;

    /**
     * 标签
     **/
    private String label;

    /**
     * 标题列表
     */
    private List<String> titleList;

    /**
     * 链接
     */
    private String url;

    /**
     * true - url 地址为空  ； false - url 地址不为空
     */
    private Boolean urlIsBlank = false;

    /**
     * 排序
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer orderNum;

    /**
     * 添加对外接口，附加信息
     */
    private JSONObject config;

    /**
     * 行为集合
     */
    private List<Action> actions;


    /**
     * 子菜单
     */
    @JSONField(name = "items")
    private List<Menu> children;


    /**
     * 菜单树
     *
     * @author liufenglong
     * @date 2020/6/18
     */
    public static class MenuBuilder {
        private List<Menu> rootMenuList = new ArrayList<Menu>();
        private Map<Integer, List<Menu>> parentIdMap = Maps.newHashMap();

        public MenuBuilder(List<Menu> menuList) {
            this.rootMenuList = menuList.stream().filter(menuNode -> menuNode.getParentId().equals(-1)).collect(Collectors.toList());
            this.parentIdMap = menuList.stream().collect(Collectors.groupingBy(e -> e.getParentId()));
        }

        /**
         * 建立树形结构
         * <p>
         * 第一级是 新大数据应用平台
         * 第二级是 默认导航、定制报表
         */
        public List<Menu> builder() {
            List<Menu> treeMenus = new ArrayList<Menu>();
            Queue<Menu> queue = new LinkedList<>();
            rootMenuList.forEach(c -> {
                if (Integer.valueOf(2).compareTo(c.getLevel()) < 0) {
                    c.setKey(c.getCode());
                    if (CollectionUtils.isEmpty(c.getTitleList())) {
                        c.setTitleList(new ArrayList<>());
                    }
                    c.getTitleList().add(c.getText());
                }
                if (StringUtils.isEmpty(c.getUrl())) {
                    c.setUrl("/" + c.getCode());
                    c.setUrlIsBlank(true);
                }
                queue.offer(c);
            });

            List<Menu> tempList;
            while (!queue.isEmpty()) {
                Menu m = queue.poll();
                tempList = parentIdMap.get(m.getId());
                if (tempList != null) {
                    tempList = tempList
                            .stream()
                            .sorted(Comparator.comparing(Menu::getOrderNum))
                            .collect(Collectors.toList());
                    m.setChildren(tempList);
                    tempList.forEach(c -> {
                        if (Integer.valueOf(2).compareTo(c.getLevel()) < 0) {
                            c.setKey(StringUtils.isEmpty(m.getKey()) ? c.getCode() : m.getKey() + "." + c.getCode());
                            if (CollectionUtils.isEmpty(c.getTitleList())) {
                                c.setTitleList(new ArrayList<>());
                            }
                            if (!CollectionUtils.isEmpty(m.getTitleList())) {
                                c.getTitleList().addAll(m.getTitleList());
                            }
                            c.getTitleList().add(c.getText());
                        }
                        if (StringUtils.isEmpty(c.getUrl())) {
                            c.setUrl("/" + c.getCode());
                            c.setUrlIsBlank(true);
                        }
                        queue.offer(c);
                    });
                }
            }
            treeMenus.addAll(rootMenuList);
            return treeMenus;
        }
    }

}

