package com.example.DADDA.service;

import com.example.DADDA.domain.DACT;
import com.example.DADDA.domain.DAPK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.MerkleTrees;
import com.example.asuredelete.service.Encrypt;
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
public class DAEncrypt {
    @Autowired
    private Encrypt encrypt;
    @SneakyThrows
    public DACT encrypt(DAPK pk, int attNum, String policy, String filePath){
        DACT ct=new DACT();
        File file=new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Element m = FuncUtils.getPairing().getGT().newElementFromBytes(fileBytes);
        Element s = FuncUtils.getRandomFromZp();
        Element c1= (pk.getEGGA().powZn(s)).mul(m);
        Element c2= pk.getH().powZn(s);
        ct.setC1(c1);
        ct.setC2(c2);
        //
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
        ct.setC31(l1);
        ct.setC32(l2);
        
        //
        Element hash = MerkleTrees.merkle_tree(l2);
        ct.setC4(hash);
        //
        Element sd = FuncUtils.getRandomFromZp();
        List<Element> l3=new ArrayList<>();
        l3.add(pk.getG().powZn(sd));
        l3.add(FuncUtils.hashFromStringToG1(FuncUtils.getRandomFromG1().toString()).powZn(sd));
        ct.setC5(l3);



        return  ct;
    }

}
