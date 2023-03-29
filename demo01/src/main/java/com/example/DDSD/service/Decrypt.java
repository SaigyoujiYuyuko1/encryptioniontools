package com.example.DDSD.service;

import com.example.DDSD.domain.DDCT;
import com.example.DDSD.domain.DDSK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.Lagrange;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Decrypt {
    @EXCTime
    public void decrypt(DDCT ct, DDSK sk,int policyNum){
        recover(policyNum);
        Element c = sk.getD2t().mul(ct.getC2t());
        Element e = FuncUtils.pairing.pairing(c, sk.getD1())
                .div(FuncUtils.pairing.pairing(FuncUtils.getRandomFromG1(), FuncUtils.getRandomFromG1()));
        Element m = ct.getC1().div(e);

    }

    private void recover(int n){
        List<Element> list=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(FuncUtils.getRandomFromG1());
        }
        for (int i = 0; i < n; i++) {
            Lagrange.PloyReco(list);
        }
    }
}
