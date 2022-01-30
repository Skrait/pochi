package com.jg.pochi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 路由视图类
 * Author Peekaboo
 * Date 2022/1/25 22:21
 */
@Data
public class RouterVo implements Serializable {
    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String  path;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 当设置noRedirect的时候该路由会在面包屑导航中不能点击
     */
    private String redirect;

    /**
     * 是否隐藏,这样就不用考虑null
     */
    private boolean hidden;

    /**
     * 是否永远展示。如果为true，即使子菜单只有一个，也会展示层级关系
     */
    private boolean alwaysShow;

    /**
     * 子菜单
     */
    private List<RouterVo> children;

    /**
     * 路由的元信息
     */
    private MetaVo meta;

    /**
     * 路由元信息
     */
    @Data
    public static class MetaVo implements Serializable {

        /**
         * 菜单名称
         */
        private String title;

        /**
         * 菜单图标
         */
        private String icon;

        public MetaVo(String title, String icon) {
            this.title = title;
            this.icon = icon;
        }
    }
}
