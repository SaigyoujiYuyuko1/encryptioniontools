package com.example.demo01;

import com.example.Demo01ApplicationTests;
import com.example.Yue.service.YueScheme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class YueTest extends Demo01ApplicationTests {
    @Autowired
    private YueScheme yueScheme;
    @Test
    public void YueTest(){
        yueScheme.YueDemo();

    }
}
