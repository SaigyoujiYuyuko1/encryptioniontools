package com.example.Xue.Service;

import com.example.Xue.domain.MSK;
import com.example.Xue.domain.PK;
import com.example.Xue.domain.SK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.access.AccessControlParameter;
import com.example.asuredelete.access.parser.ParserUtils;
import com.example.asuredelete.access.parser.PolicyParser;
import com.example.asuredelete.access.tree.AccessTreeEngine;
import com.example.asuredelete.service.Encrypt;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KenGen {
    @Autowired
    private Encrypt encrypt;
    public SK keyGen(PK pk, MSK msk,String policy){
        SK sk=new SK();
        Map<String, Element> leafNodes = encrypt.genAccessTree(policy);
        Element r= FuncUtils.getRandomFromZp();
        Element g = pk.getG();
        Element h = pk.getH();
        sk.setGr(g.powZn(r));

        Element d1 = h.powZn(msk.getY());
        Element re=FuncUtils.getOneFromG1();
        List<List<Element>> list = msk.getTij();
        for (int i = 0; i < leafNodes.size(); i++) {
            for (Element e : list.get(i)) {
                re=re.mul(e);
            }
        }
        Element d2 = (g.powZn(re)).powZn(r);
        sk.setDw(d1.mul(d2));
        sk.setLeafNodes(leafNodes);



        return sk;
    }

    public void completeSk(PK pk,SK sk){
        Element g = pk.getG();
        Element alpha=FuncUtils.getRandomFromZp();
        sk.setAlpha(alpha);
        sk.setG_alpha(g.powZn(alpha));
    }


}
