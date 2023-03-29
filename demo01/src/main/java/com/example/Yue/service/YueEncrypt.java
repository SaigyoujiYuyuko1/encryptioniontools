package com.example.Yue.service;

import com.example.Yue.doamin.YueCT;
import com.example.Yue.doamin.YuePK;
import com.example.Yue.doamin.YueTD;
import com.example.asuredelete.Utils.AES;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class YueEncrypt {
    @Autowired
    private com.example.asuredelete.service.Encrypt encrypt;
    @SneakyThrows
    @EXCTime
    public YueCT encrypt(YuePK pk, String filePath,String policy,int attNum){
        YueCT ct=new YueCT();
        File file=new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        //AES
        SecretKey aesSK = AES.generateKey();
        byte[]   ciphertext  = AES.encrypt(fileBytes.toString(), aesSK);

        ct.setC0(ciphertext);
        //CP-ABE,加密对称密钥
        Element m = FuncUtils.getPairing().getGT().newElementFromBytes(aesSK.toString().getBytes());
        Element s1 = FuncUtils.getRandomFromZp();
        Element s2 = FuncUtils.getRandomFromZp();
        Element s3 = FuncUtils.getRandomFromZp();
        Element sp=s1.mul(s2);
        ct.setC1(m.mul(pk.getEggAlpha().powZn(sp)));

        ct.setC2(pk.getGBeta().powZn(sp));


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


        //TD
        Element rt = FuncUtils.getRandomFromZp();
        YueTD td=new YueTD();
        td.setX(g.powZn(rt));
        td.setY(s2);
        Element temp = FuncUtils.getPairing().pairing(FuncUtils.hashFromBytesToG1("1".getBytes()), pk.getGDelta());
        td.setZ(s3.add(FuncUtils.hashFromBytesToZp(temp.powZn(rt).toString().getBytes())));
        ct.setTd(td);
        return ct;
    }
}
