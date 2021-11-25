package com.jg.pochi.pojo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO代表服务层需要接收的数据和返回的数据，而VO代表展示层需要显示的数据。
 * 登录Token返回视图类
 * Author Peekaboo
 * Date 2021/11/25 9:05
 */
@Data
public class TokenVo implements Serializable {

    /**
     * 这里Token用Serializable类型定义减少了类型转换的繁琐
     * 登录时返回的Token
     */
    private Serializable token;

    public TokenVo(Serializable token) {
        this.token = token;
    }
}
