package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.domain.MSK;
import com.example.asuredelete.domain.PK;
import com.example.asuredelete.domain.Parameter;
import com.example.asuredelete.domain.SK;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Setup {
   private Pairing pair = FuncUtils.getPairing();

    public Parameter setupPP(){
       return new Parameter(
               pair.getG1(),
                pair.getZr(),
                FuncUtils.getRandomFromG1(),
                FuncUtils.getRandomFromZp(),
                FuncUtils.getRandomFromZp()

        );

    }

    public PK setupPK(Parameter pp){
        PK pk=new PK();
        pk.setG1(pp.getG1());
        pk.setG(pp.getG());
        pk.setH(pp.getG().powZn(pp.getAlpha()));
        pk.setF(pp.getG().powZn(FuncUtils.getOneFromZp().div(pp.getBeta())));
        pk.setEGGA(pair.pairing(pp.getG(),pp.getG()).powZn(pp.getAlpha()));
        return pk;
    }
    public MSK setupMSK(Parameter pp){
        Element sk =  pp.getG().powZn(pp.getAlpha());
        List<Element> list=new ArrayList<>();

        MSK msk=new MSK();
        msk.setBeta(pp.getBeta());
        msk.setGAlpha(sk);
        return msk;
    }

    public SK ketGen(MSK msk,PK pk,Parameter pp,int num){
        SK sk=new SK();
        Element r = FuncUtils.getRandomFromZp();
        Element g = pp.getG();
        Element alpha = pp.getAlpha();
        Element beta = msk.getBeta();
        Element d = (alpha.add(r)).div(beta);
        List<Element> l1=new ArrayList<>();
        List<Element> l2=new ArrayList<>();
        sk.setD(d);
        for (int i = 0; i < num; i++) {
            Element t = FuncUtils.getRandomFromZp();
            Element t1 = g.powZn(r);
            Element t2 = FuncUtils.hashFromStringToG1(String.valueOf(i)).powZn(t);
            Element d1 = t1.mul(t2);
            Element d2 = g.powZn(t);
            l1.add(d1);
            l2.add(d2);
        }
        sk.setL1(l1);
        sk.setL2(l2);
        return sk;
    }
}
