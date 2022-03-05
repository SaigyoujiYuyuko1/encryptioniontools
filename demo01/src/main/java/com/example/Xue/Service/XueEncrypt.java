package com.example.Xue.Service;

import com.example.Xue.domain.PK;
import com.example.Xue.domain.XueCT;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.MerkleTrees;
import it.unisa.dia.gas.jpbc.Element;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class XueEncrypt {

    @Autowired
    private MerkleTrees merkleTrees;

    @SneakyThrows
    public XueCT encrypt(PK pk,int policyNum,String filePath){
        File file=new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Element m = FuncUtils.getPairing().getG1().newElementFromBytes(fileBytes);
        Element s = FuncUtils.getRandomFromZp();
        Element c1=pk.getY().powZn(s).mul(m);
        Element c2=pk.getG().powZn(s);
        List<List<Element>> tij = pk.getTij();
        List<List<Element>> res=new ArrayList<>();
        List<Element> mthList=new ArrayList<>();
        for (int i = 0; i < policyNum; i++) {
            List<Element> temp=new ArrayList<>();
            for (Element e : tij.get(i)) {
                temp.add(e.powZn(s));
                mthList.add(e.powZn(s));
            }
            res.add(temp);
        }
        XueCT ct=new XueCT();
        ct.setC1(c1);
        ct.setC2(c2);
        ct.setC3(res);
        Element mthRoot = merkleTrees.merkle_tree(mthList);


        return ct;
    }
}
