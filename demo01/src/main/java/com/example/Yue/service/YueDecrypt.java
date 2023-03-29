package com.example.Yue.service;

import com.example.Yue.doamin.YueCT;
import com.example.Yue.doamin.YuePK;
import com.example.Yue.doamin.YueSK;
import com.example.Yue.doamin.YueTD;
import com.example.asuredelete.Utils.AES;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.Lagrange;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class YueDecrypt {
    @SneakyThrows
    @EXCTime
    public void decrypt(YueCT ct, YueSK sk, YuePK pk,String filePath, int policyNum){
        recover(policyNum);
        Element g = pk.getG();
        YueTD td = ct.getTd();
        Element fr = FuncUtils.pairing.pairing(g, g).powZn(td.getY());
        Element div = FuncUtils.pairing.pairing(ct.getC2(), sk.getD1());
        Element aessk = ct.getC1().div(div.div(fr));
        long s = System.currentTimeMillis();
        SecretKey ssk = AES.generateKey();


        File file=new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        byte[] cipher = AES.encrypt(fileBytes.toString(), ssk);
        System.out.println("AES加密时间："+(System.currentTimeMillis()-s));
        String decrypt = AES.decrypt(cipher, ssk);

    }

    private void recover(int n){
        List<Element> list=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(FuncUtils.getRandomFromG1());
        }
        for (int i = 0; i < n; i++) {
            Lagrange.PloyReco(list);
        }
    }
}
