package com.example.demo02;

import com.example.Demo01Application;
import com.example.asuredelete.Utils.FuncUtils;
import org.junit.Test;

public class FuncTest extends Demo01Application {
    @Test
    public void funcTest(){
        for (int i=0;i<10;i++){
            System.out.println(FuncUtils.getRandomFromG1());
        }
    }
}
