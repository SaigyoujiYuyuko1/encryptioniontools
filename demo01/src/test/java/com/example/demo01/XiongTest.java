package com.example.demo01;

import com.example.Demo01ApplicationTests;
import com.example.SelfDoc.service.XiongScheme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class XiongTest extends Demo01ApplicationTests {
    @Autowired
    private XiongScheme xiongScheme;
    @Test
    public void Xiong(){
        xiongScheme.XiongDemo();
    }
}
