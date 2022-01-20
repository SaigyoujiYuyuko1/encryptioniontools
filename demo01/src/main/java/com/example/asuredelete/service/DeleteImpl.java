package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.domain.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeleteImpl {
    private Pairing pairing = FuncUtils.pairing;
    static  int mid=DeleteRequest.mid;
    int limit=BigDFastTransfer.lim;
    double PI =Math.acos(-1);
     BigDecimal zero=BigDFastTransfer.zero;
     BigDecimal one=BigDFastTransfer.one;
    int type=-1;

    public void delImpl(Parameter pp,CT ct, DR dr){
        List<RCNode> rcNodes = ct.getRcNodesList();
        List<Element> ployA = dr.getPloyA();
        List<Element> ployB = dr.getPloyB();
        Element g = pp.getG();
        List<Element> abm=new ArrayList<>();
        for (int i = 0; i < ployA.size(); i++) {
            Element res = this.pairing.pairing(ployA.get(i), ployB.get(i));
            abm.add(res);
        }

    }
    public void proofGen(Parameter pp,List<Element> abm){
        //计算A*B的秘密值(生成证据)
        final Element[] temp = {FuncUtils.getOneFromG1()};
        abm.parallelStream().forEach(p->{
            temp[0] = temp[0].mul(p);
        });
        Element result=temp[0];
        BigComplex omg=new BigComplex(new BigDecimal(String.valueOf(Math.cos(PI/mid))),
                new BigDecimal(String.valueOf(type * Math.sin(PI/mid))));
        BigComplex w=new BigComplex(one,zero);
        BigComplex res=new BigComplex(zero,zero);
        for (int i = 0; i <(limit>>1) ; i++) {
           w = BigComplex.Mul(omg, w);
            BigComplex pow = w.pow(i);
            res=BigComplex.Add(res,pow);

        }
        Element g = pp.getG();
        g.powZn(FuncUtils.getPairing().getZr().newElement(new BigInteger(res.toString())));
    }
}
