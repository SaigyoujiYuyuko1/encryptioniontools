package com.example.Xue.Service;

import com.example.Xue.domain.ReKey;
import com.example.Xue.domain.XueCT;
import com.example.Xue.domain.XueDR;
import com.example.Xue.domain.XueMSK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.MerkleTrees;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReEncrypt {
    @Autowired
    private MerkleTrees merkleTrees;

    public ReKey reEncKeyGen(XueDR dr, XueMSK msk){
        Element tij = FuncUtils.getRandomFromZp();
        Integer index = dr.getAttr();
        Element t = msk.getTij().get(index).get(index);
        Element cki = tij.div(t);
        ReKey key=new ReKey();
        key.setAtt(index);
        key.setCki(cki);
        key.setFileName(dr.getFileName());
        return key;

    }

    public Element reEnc( XueCT ct,ReKey rk){
        List<List<Element>> c3 = ct.getC3();
        Integer index = rk.getAtt();
        Element cki = rk.getCki();
        List<Element> temp = c3.get(index);
        Element res = temp.get(index).powZn(cki);
        temp.set(index,res);
        c3.set(index,temp);
        List<Element> leaf=new ArrayList<>();
        for (List<Element> list : c3) {
            for (Element e : list) {
                leaf.add(e);
            }
        }
        return merkleTrees.merkle_tree(leaf);


    }
}
