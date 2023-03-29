package com.example.DADDA.service;

import com.example.DADDA.domain.DACT;
import com.example.DADDA.domain.DAReq;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.MerkleTrees;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DADeleteExec {
    public Element deleteExec(DAReq req, DACT ct){
        List<Element> c5 = ct.getC5();
        Pairing pairing = FuncUtils.pairing;
        Element p1 = pairing.pairing(c5.get(0), req.getRd());
        Element p2 = pairing.pairing(c5.get(1), req.getRd());
        c5.set(0,p1);
        c5.set(1,p2);
        ct.setC5(c5);
        //
        Element hash = MerkleTrees.merkle_tree(ct.getC32());
        List<Element> lh=new ArrayList<>();
        lh.add(hash);
        lh.add(FuncUtils.getRandomFromG1());
        return MerkleTrees.merkle_tree(lh);

    }
}
