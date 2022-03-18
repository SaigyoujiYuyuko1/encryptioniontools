package com.example.Xue.Service;

import com.example.Xue.domain.XueMSK;
import com.example.Xue.domain.XuePK;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class XueSetup {
    public void setup(XuePK xuePk, XueMSK msk, int n){
        xuePk.setG(FuncUtils.getRandomFromG1());
        xuePk.setH(FuncUtils.getRandomFromG1());
        xuePk.setY(FuncUtils.pairing.pairing(xuePk.getG(), xuePk.getH()));
        List<List<Element>> resMsk=new ArrayList<>();
        List<List<Element>> resPk=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Element> tempPk=new ArrayList<>();
            List<Element> tempMsk=new ArrayList<>();
            for (int j = 0; j < n; j++) {
                Element t=FuncUtils.getRandomFromZp();
                tempMsk.add(t);
                tempPk.add(xuePk.getG().powZn(t));
            }
            resMsk.add(tempMsk);
            resPk.add(tempPk);

        }

        xuePk.setTij(resPk);
        msk.setY(FuncUtils.getRandomFromZp());
        msk.setTij(resMsk);

    }
}
