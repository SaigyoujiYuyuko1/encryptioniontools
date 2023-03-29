package com.example.DDSD.service;

import com.example.DDSD.domain.DDCT;
import com.example.DDSD.domain.DDPK;
import com.example.asuredelete.Utils.FuncUtils;
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
public class DDSDEncrypt {


    @Autowired
    private SKGen skGen;
    @Autowired
    private com.example.asuredelete.service.Encrypt encrypt;


    public  Element flag;
    @SneakyThrows
    @EXCTime
    public DDCT encrypt(DDPK pk, String filePath,int state,String policy,int attNum){
        DDCT ct=new DDCT();
        File file=new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Element m = FuncUtils.getPairing().getGT().newElementFromBytes(fileBytes);
        Element s = FuncUtils.getRandomFromZp();
        ct.setC1(m.mul(pk.getEggAlpha().powZn(s)));
        Element st = skGen.getSt(pk, state);
        Element h = pk.getH();
        this.flag=h.powZn(s);

        ct.setC2t(h.powZn(s.sub(st)));


        List<Element> l1=new ArrayList<>();
        List<Element> l2=new ArrayList<>();
        Element g = pk.getG();
        for (int i = 0; i < attNum; i++) {
            Map<String, Element> leafNodes = encrypt.genAccessTree(policy);
            leafNodes.entrySet().parallelStream().forEach(entry->{
                l1.add(g.powZn(entry.getValue()));
                l2.add((FuncUtils.hashFromBytesToG1(entry.getKey().getBytes())).powZn(entry.getValue()));
            });
        }

        ct.setC31(l1);
        ct.setC32(l2);



        return ct;
    }
}
