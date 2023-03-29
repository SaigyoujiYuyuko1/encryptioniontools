package com.example.DADDA.service;

import com.example.DADDA.domain.DAMSK;
import com.example.DADDA.domain.DAPK;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

@Service
public class DASetup {
    public void setup(DAMSK msk, DAPK pk){
        Element alpha = FuncUtils.getRandomFromZp();
        Element beta = FuncUtils.getRandomFromZp();
        pk.setG(FuncUtils.getRandomFromG1());
        pk.setH(FuncUtils.getRandomFromG1().powZn(beta));
        pk.setF(pk.getG().powZn(FuncUtils.getOneFromZp().div(beta)));
        pk.setEGGA(FuncUtils.pairing.pairing(pk.getG(),pk.getG() ).powZn(alpha));
        //
        msk.setBeta(beta);
        msk.setGAlpha(pk.getG().powZn(alpha));

    }

}
