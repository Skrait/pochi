package com.jg.pochi.controller;

import com.jg.pochi.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Peekaboo
 *
 * @Date 2021/11/20 14:34
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<?> test() {
        return new Result<>("操作成功");
    }

}