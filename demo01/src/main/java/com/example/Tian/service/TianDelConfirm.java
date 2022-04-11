package com.example.Tian.service;

import com.example.Tian.domain.GK;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class TianDelConfirm {
    public Boolean confirm(GK gk, List<Element> att, Map<Element,Element> map){
        //计算自身属性
        List<Element> hb=new ArrayList<>();
        Element one = FuncUtils.getOneFromG1();
        for (int i = 0; i < att.size(); i++) {
            Element ai = FuncUtils.getRandomFromZp();
            map.put(att.get(i),ai);
            Element hi = FuncUtils.hashFromBytesToG1(att.get(i).toBytes()).powZn(ai);
            one=one.mul(hi);
            hb.add(one);
        }
        Set<Element> hSet = gk.getHb();
        for (Element e : hb) {
            if(hSet.contains(e)){
                return false;
            }
        }

        return true;
    }


}
