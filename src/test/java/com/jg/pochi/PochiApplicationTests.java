package com.jg.pochi;

import com.jg.pochi.mapper.SysLogMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PochiApplicationTests {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Test
    void contextLoads() {
    }

}
