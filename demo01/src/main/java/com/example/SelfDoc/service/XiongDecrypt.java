package com.example.SelfDoc.service;

import com.example.SelfDoc.domain.XiongCT;
import com.example.SelfDoc.domain.XiongPk;
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
public class XiongDecrypt {
    @SneakyThrows
    @EXCTime
    public void decrypt(XiongCT ct, XiongPk pk,String filePath,int attNum){
        Element ca = ct.getCa();
        Element m = ca.div(pk.getEggy().powZn(FuncUtils.getRandomFromZp()));


        recover(attNum);
        File file=new File(filePath);
        SecretKey ssk = AES.generateKey();
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        byte[] cipher = AES.encrypt(fileBytes.toString(), ssk);
        String decrypt = AES.decrypt(cipher, ssk);
        List<Element> list=new ArrayList<>();
        for (int i = 0; i < attNum*2; i++) {
            list.add(FuncUtils.getRandomFromG1());
        }
        Lagrange.PloyReco(list);


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
