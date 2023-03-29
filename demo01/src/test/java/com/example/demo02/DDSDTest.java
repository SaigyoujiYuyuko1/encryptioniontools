package com.example.demo02;

import com.example.DDSD.service.DDSDScheme;
import com.example.asuredelete.aop.EXCTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DDSDTest {
    @Autowired
    private DDSDScheme ddsdScheme;

    @Test
    @EXCTime
    public void test(){
        ddsdScheme.ddsdDemo();
    }
}
