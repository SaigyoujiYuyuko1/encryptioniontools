package com.example.Tian.service;

import com.example.Tian.domain.GK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.Lagrange;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TianDownLoad {
    @Autowired
    private TianUpload tianUpload;

    @EXCTime
    public Element dec(GK gk,Element ct, List<Element> att, Map<Element,Element> map){
        //计算自身属性
        Element hb1 = FuncUtils.getOneFromG1();
        for (int i = 0; i < att.size(); i++) {
            Element ai = map.get(att.get(i));
            Element hi = FuncUtils.hashFromBytesToG1(att.get(i).toBytes()).powZn(ai);
            hb1=hb1.mul(hi);
        }

        Set<Element> hb = gk.getHb();
        //csp判断
//        if(!hb.contains(hb1)){
//            log.info("属性不符合");
//        }
        String rsaPK=KeyManger.getRsaPK();
        List<String> bCK = gk.getBCK();


        List<Element> rList=new ArrayList<>();
        List<Element> randList = tianUpload.addRandom(bCK, rsaPK, rList);
        //未加随机数的解密
        List<String> decList = KeyManger.decString(bCK);

        List<Element> ckList = tianUpload.removeRandom(decList, rList);
        List<Element> dDK = gk.getDdk();
        AtomicInteger i=new AtomicInteger(0);
        List<Element> dkList = dDK.stream().map(dk -> dk.div(ckList.get(i.getAndIncrement()))).collect(Collectors.toList());
        Element dk = Lagrange.PloyReco(dkList);
        //解密
        return ct.div(dk);

    }


}
