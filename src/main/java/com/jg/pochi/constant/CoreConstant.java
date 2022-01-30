package com.jg.pochi.constant;

/**
 * Author Peekaboo
 * 核心常量
 * @Date 2021/11/23 14:55
 */
public class CoreConstant {

    //被static关键字修饰的方法或者变量不需要依赖于对象来进行访问，
    // 只要类被加载了，就可以通过类名去进行访问。
    /**
     * 请求头携带的Token的Key
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 默认父级菜单ID
     */
    public static final Long DEFAULT_PARENT_ID = 0L;

    /**
     * 菜单默认不显示
     */
    public static final Integer HIDDEN_STATE = 0;

    /**
     * 默认不跳转
     */
    public static final String NO_REDIRECT = "noRedirect";

    /**
     * 路径间隔符
     */
    public static final String URL_SPLIT = "/";

    /**
     * 菜单默认组件地址
     */
    public static final String DEFAULT_COMPONENT = "Layout";
}
