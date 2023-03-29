package com.example.demo02;

import com.example.Demo01ApplicationTests;
import com.example.Xue.Service.XueScheme;
import com.example.asuredelete.service.CSAD;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComparableTest extends Demo01ApplicationTests {
    @Autowired
    private CSAD csad;
    @Autowired
    private XueScheme xueScheme;


    @Test
    public  void  timeTest(){
//        csad.CSADDemo();
        xueScheme.XueDemo();
    }
}
