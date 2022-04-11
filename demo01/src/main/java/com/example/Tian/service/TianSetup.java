package com.example.Tian.service;

import com.example.Tian.domain.GK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.Lagrange;
import com.example.asuredelete.Utils.RSAUtils;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j

public class TianSetup {

    public GK Setup(List<Element> att,int t,int n,Map<Element,Element> map){
        GK gk=new GK();
        Element dk = FuncUtils.getRandomFromG1();
        List<Element> dki = Lagrange.PloyDiv(dk, t, n);
        List<Element> cki=new ArrayList<>();

        String rsaPK= KeyManger.getRsaPK();
        List<String> bCK=new ArrayList<>();
        List<Element> Ddk=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Element ckt = FuncUtils.getRandomFromG1();
            Ddk.add(dki.get(i).mul(ckt));
            cki.add(ckt);
            bCK.add(RSAUtils.encryptByPublicKey(ckt.toBytes(), rsaPK));
        }
        Set<Element> hb=new HashSet<>();
        Element one = FuncUtils.getOneFromG1();
        for (int i = 0; i < att.size(); i++) {
            Element ai = FuncUtils.getRandomFromZp();
            map.put(att.get(i),ai);
            Element hi = FuncUtils.hashFromBytesToG1(att.get(i).toBytes()).powZn(ai);
            one=one.mul(hi);
            hb.add(one);
        }

        gk.setBCK(bCK);
        gk.setDdk(dki);
        gk.setHb(hb);

        return gk;
    }

}
