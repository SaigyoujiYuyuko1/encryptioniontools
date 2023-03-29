package com.example.demo02;

import com.example.DADDA.service.DAScheme;
import com.example.Demo01ApplicationTests;
import com.example.Xue.Service.XueScheme;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class XueTest extends Demo01ApplicationTests {
    @Autowired
    private XueScheme xueScheme;
    @Autowired
    private DAScheme daScheme;


    @Test
    public void xueTest(){
        long s = System.currentTimeMillis();
        xueScheme.XueDemo();
        long e = System.currentTimeMillis();
        log.info("Xue的总共时间：{}",e-s);
    }
    @Test
    public void daTest(){
        daScheme.dadda();

    }
}
