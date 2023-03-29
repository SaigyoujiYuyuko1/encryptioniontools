package com.example.Tian.service;

import com.example.Tian.domain.GK;
import com.example.asuredelete.aop.EXCTime;
import com.google.common.collect.Iterables;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TianDel {
    @EXCTime
    public GK updateKey(GK gk,List<Element> delAttList){
        List<Element> Ddk = gk.getDdk();
        List<String> bCk = gk.getBCK();
        int len = delAttList.size();
        for (int i = 0; i < len; i++) {
            int  index = (int)(Math.random() * (len>>1));
            Ddk.set(index,null);
            bCk.set(index,null);
        }
        Iterables.removeIf(Ddk, Objects::isNull);
        Iterables.removeIf(bCk, Objects::isNull);
        //删除RSA私钥h
        return gk;
    }
}
