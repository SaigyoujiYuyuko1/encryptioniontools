package com.example.Yue.service;

import com.example.Yue.doamin.YueMSK;
import com.example.Yue.doamin.YuePK;
import com.example.Yue.doamin.YueSK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class YueKeyGen {
    @EXCTime
    public YueSK keyGen(YuePK pk, YueMSK msk,int attNum){
        YueSK sk=new YueSK();
        Element g = pk.getG();
        List<Element> l1=new ArrayList<>();
        List<Element> l2=new ArrayList<>();
        Element r = FuncUtils.getRandomFromZp();
        msk.setR(r);
        msk.setGr(g.powZn(r));
        for (int i = 0; i < attNum; i++) {
            Element ri = FuncUtils.getRandomFromZp();
            Element hash = FuncUtils.hashFromBytesToG1(("" + i).getBytes()).powZn(ri);
            l1.add((g.powZn(r)).mul(hash));
            l2.add(g.powZn(ri));
        }
        sk.setD21(l1);
        sk.setD22(l2);

        Element gAlpha = msk.getGAlpha();
        Element div = FuncUtils.getOneFromZp().div(msk.getBeta());
        sk.setD1((gAlpha.mul(g.powZn(r))).powZn(div));




        return sk;
    }
}
