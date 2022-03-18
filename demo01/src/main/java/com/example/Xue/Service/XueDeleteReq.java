package com.example.Xue.Service;

import com.example.Xue.domain.*;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.MerkleTrees;
import com.example.asuredelete.Utils.RSASign;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class XueDeleteReq {
    @Autowired
    private MerkleTrees merkleTrees;
        public XueDR genDR(String fileName, int n, XueMSK msk){
            XueDR dr=new XueDR();
            dr.setFileName(fileName);
            dr.setAttr(n);
            dr.setValid(FuncUtils.getRandomFromZp());
            dr.setInValid(msk.getTij().get(n).get(0));

            return dr;
        }

        public XueCspRe cspResponse(XuePK pk , int att, UploadFile up){
            byte[] sign = up.getSign();
            Element x = up.getCt().getC3().get(att).get(att);
            Element rou = up.getRou();



            XueCspRe re=new XueCspRe();
            re.setX(x);
            re.setIndex(att);
            re.setRou(rou);
            re.setOmiga(FuncUtils.getRandomFromZp());
            re.setSign(sign);
            return re;

        }

        public boolean userVerify(String fileName,XueCspRe re,XuePK pk,XueCT ct){
            Element rou = re.getRou();
            Integer att = re.getIndex();
            Element x = re.getX();
            Pairing pairing = FuncUtils.getPairing();
            Element left = pairing.pairing(rou, pk.getG());
            StringBuilder sb=new StringBuilder();
            sb.append(fileName);
            sb.append(att);
            sb.append(x.toString());
            Element hash = FuncUtils.hashFromStringToG1(sb.toString());
            Element v = pk.getGAlpha();
            Element right = pairing.pairing(hash, v);


            List<List<Element>> c3 = ct.getC3();
            List<Element> mth=new ArrayList<>();
            for (List<Element> list : c3) {
                for (Element e : list) {
                    mth.add(e);
                }
            }
            byte[] sign1 = re.getSign();

            Element r1 = merkleTrees.merkle_tree(mth);
            byte[] sign2 = RSASign.signFile(RSASign.getPrivateKey(), r1.toBytes());
            if(left.isEqual(right)&& Arrays.equals(sign1, sign2)){
                return true;
            }

            return false;
        }
}
