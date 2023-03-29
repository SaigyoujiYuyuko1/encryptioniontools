package com.example.Xue.Service;

import com.example.Xue.domain.XueMSK;
import com.example.Xue.domain.XuePK;
import com.example.Xue.domain.XueSK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.RSASign;
import com.example.asuredelete.service.Encrypt;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class XueKenGen {
    @Autowired
    private Encrypt encrypt;
    public XueSK keyGen(XuePK xuePk, XueMSK msk, String policy){
        XueSK xueSk =new XueSK();
        Map<String, Element> leafNodes = encrypt.genAccessTree(policy);
        Element r= FuncUtils.getRandomFromZp();
        Element g = xuePk.getG();
        Element h = xuePk.getH();
        xueSk.setGr(g.powZn(r));

        Element d1 = h.powZn(msk.getY());
        Element re=FuncUtils.getOneFromZp();
        List<List<Element>> list = msk.getTij();
        for (int i = 0; i < leafNodes.size(); i++) {
            for (Element e : list.get(i)) {
                re=re.mul(e);
            }
        }
        Element d2 = (g.powZn(re)).powZn(r);
        xueSk.setDw(d1.mul(d2));
        xueSk.setLeafNodes(leafNodes);
        xueSk.setSsk(RSASign.getPrivateKey());



        return xueSk;
    }

    public void completeSk(XuePK xuePk, XueSK xueSk){
        Element g = xuePk.getG();
        Element alpha=FuncUtils.getRandomFromZp();
        xueSk.setAlpha(alpha);
        xuePk.setGAlpha(g.powZn(alpha));
    }


}
