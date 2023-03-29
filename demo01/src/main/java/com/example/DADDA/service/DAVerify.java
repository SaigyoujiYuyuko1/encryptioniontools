package com.example.DADDA.service;

import com.example.DADDA.domain.DACT;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DAVerify {
    public boolean verify(DACT ct,Element proof){

        List<Element> l1=new ArrayList<>();
        l1.add(ct.getC4());
        byte[] bytes = ct.getC5().get(0).add(ct.getC5().get(0)).toBytes();
        FuncUtils.hashFromStringToG1(ct.getC4().add(FuncUtils.getPairing().getG1().newElementFromBytes(bytes)).toString());

        return true;
    }
}
