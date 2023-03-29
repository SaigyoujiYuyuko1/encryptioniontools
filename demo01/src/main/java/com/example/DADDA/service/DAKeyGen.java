package com.example.DADDA.service;

import com.example.DADDA.domain.DAMSK;
import com.example.DADDA.domain.DAPK;
import com.example.DADDA.domain.DASK;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DAKeyGen {
    public DASK keyGen(DAPK pk, DAMSK msk, int attNum){
        DASK sk=new DASK();
        Element g = pk.getG();
        List<Element> l1=new ArrayList<>();
        List<Element> l2=new ArrayList<>();
        Element r = FuncUtils.getRandomFromZp();
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
        //
        List<Element> l3=new ArrayList<>();
        Element rd = FuncUtils.getRandomFromZp();
        l3.add(g.powZn(r).mul(FuncUtils.hashFromStringToG1(FuncUtils.getRandomFromG1().toString()).powZn(rd)));
        l3.add(g.powZn(rd));
        sk.setD3(l3);


        return sk;
    }
}
