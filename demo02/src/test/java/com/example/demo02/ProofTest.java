package com.example.demo02;

import com.example.Demo02Application;
import com.example.service.ProofGenService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

//import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Demo02Application.class)
public class ProofTest  {
    @Autowired
    private ProofGenService proofGenService;
    @Test
    public void proofGenTest(){
        proofGenService.IDFT();
    }


    @Test
    public void mockTest(){
        List mockedList = Mockito.mock(ArrayList.class);
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //这里默认是判断该方法调用times(1),同下
        Mockito.verify(mockedList).add("once");
        Mockito.verify(mockedList, Mockito.times(1)).add("once");


        Mockito.verify(mockedList, Mockito.times(2)).add("twice");
        Mockito.verify(mockedList, Mockito.times(3)).add("three times");
        //从没调用，times(0)
        Mockito.verify(mockedList, Mockito.never()).add("never happened");
        //最少一次，最少几次，最多几次
        Mockito.verify(mockedList, Mockito.atLeastOnce()).add("three times");
        Mockito.verify(mockedList, Mockito.atLeast(2)).add("three times");
        Mockito.verify(mockedList, Mockito.atMost(5)).add("three times");

    }

}
