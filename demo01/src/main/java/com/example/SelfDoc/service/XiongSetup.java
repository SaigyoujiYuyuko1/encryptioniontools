package com.example.SelfDoc.service;

import com.example.SelfDoc.domain.XiongMSK;
import com.example.SelfDoc.domain.XiongPk;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class XiongSetup {
    @EXCTime
    public void setup(XiongMSK msk, XiongPk pk,int attNum){
        Element g = FuncUtils.getRandomFromG1();
        pk.setG(g);
        pk.setN(10);
        pk.setD(5);
        pk.setU(10);
        pk.setV(5);

        //msk
        List<Element> l1=new ArrayList<>();
        List<Element> l2=new ArrayList<>();
        for (int i = 0; i < attNum; i++) {
            Element ti = FuncUtils.getRandomFromZp();
            l1.add(g.powZn(ti));
            l2.add(ti);
        }
        pk.setGtl(l1);
        msk.setTl(l2);
        Element y = FuncUtils.getRandomFromZp();
        msk.setY(y);
        pk.setEggy(FuncUtils.pairing.pairing(g,g).powZn(y));
    }
}
