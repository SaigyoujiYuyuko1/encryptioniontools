package com.example.DDSD.service;

import com.example.DDSD.domain.DDCT;
import com.example.DDSD.domain.DDPK;
import com.example.DDSD.domain.DDUP;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CTUpdate {
    public DDCT update(DDUP up, DDPK pk, DDCT ct){
        Element c2t = ct.getC2t();
        Element proof = c2t.mul(up.getUp2());
        ct.setC2t(proof);
        return ct;
    }

    public static void main(String[] args) {
        Map<Element,Integer> map=new HashMap<>();
        Element g = FuncUtils.getRandomFromG1();

        map.put(g,1);

        System.out.println(map.containsKey(g));
    }
}
