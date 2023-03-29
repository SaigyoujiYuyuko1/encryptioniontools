package com.example.Yue.service;

import com.example.Yue.doamin.YueMSK;
import com.example.Yue.doamin.YuePK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

@Service
public class YueSetup {
    @EXCTime
    public void setup(YuePK pk, YueMSK msk){
        Element g = FuncUtils.getRandomFromG1();
        Element alpha = FuncUtils.getRandomFromZp();
        Element beta = FuncUtils.getRandomFromZp();
        Element delta  = FuncUtils.getRandomFromZp();
        msk.setGAlpha(g.powZn(alpha));
        msk.setBeta(beta);
        msk.setDelta(delta);
        //
        pk.setG(g);
        pk.setGBeta(g.powZn(beta));
        pk.setGDelta(g.powZn(delta));
        pk.setEggAlpha(FuncUtils.getPairing().pairing(g,g).powZn(alpha));
    }
}
