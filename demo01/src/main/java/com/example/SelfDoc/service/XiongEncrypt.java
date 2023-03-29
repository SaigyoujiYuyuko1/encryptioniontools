package com.example.SelfDoc.service;

import com.example.SelfDoc.domain.XiongCT;
import com.example.SelfDoc.domain.XiongMSK;
import com.example.SelfDoc.domain.XiongPk;
import com.example.asuredelete.Utils.AES;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.Lagrange;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class XiongEncrypt {
    @Autowired
    private com.example.asuredelete.service.Encrypt encrypt;


    @SneakyThrows
    @EXCTime
    public XiongCT encrypt(XiongPk pk, XiongMSK msk, String filePath,String policy ,int attNum){
        XiongCT ct=new XiongCT();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        byte[] ctSe = AES.encrypt(new String(file), AES.generateKey());
        Element j = FuncUtils.getRandomFromZp();
        Element ca = pk.getEggy().powZn(j);
        ct.setCa(ca);


        List<Element> l1=new ArrayList<>();
        List<Element> l2=new ArrayList<>();
        Element g = pk.getG();

        for (int i = 0; i < attNum; i++) {
            Map<String, Element> leafNodes = this.encrypt.genAccessTree(policy);
            leafNodes.entrySet().parallelStream().forEach(entry->{
                l1.add(g.powZn(entry.getValue()));
                l2.add((FuncUtils.hashFromBytesToG1(entry.getKey().getBytes())).powZn(entry.getValue()));
            });
        }
        ct.setL1(l1);
        ct.setL1(l2);
        for (int i = 0; i < attNum; i++) {

        List<Element> list = Lagrange.PloyDiv(FuncUtils.getRandomFromG1(), attNum, attNum * 5);
        }


        return ct;
    }
}
