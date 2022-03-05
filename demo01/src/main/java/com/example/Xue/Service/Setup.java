package com.example.Xue.Service;

import com.example.Xue.domain.MSK;
import com.example.Xue.domain.PK;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Setup {
    public void setup(PK pk, MSK msk,int n){
        pk.setG(FuncUtils.getRandomFromG1());
        pk.setH(FuncUtils.getRandomFromG1());
        pk.setY(FuncUtils.pairing.pairing(pk.getG(),pk.getH()));
        List<List<Element>> resMsk=new ArrayList<>();
        List<List<Element>> resPk=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Element> tempPk=new ArrayList<>();
            List<Element> tempMsk=new ArrayList<>();
            for (int j = 0; j < n; j++) {
                Element t=FuncUtils.getRandomFromZp();
                tempMsk.add(t);
                tempPk.add(pk.getG().powZn(t));
            }
            resMsk.add(tempMsk);
            resPk.add(tempPk);

        }

        pk.setTij(resPk);
        msk.setY(FuncUtils.getRandomFromZp());
        msk.setTij(resMsk);

    }
}
