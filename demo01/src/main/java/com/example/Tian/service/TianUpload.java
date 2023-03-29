package com.example.Tian.service;

import com.example.Tian.domain.GK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.Lagrange;
import com.example.asuredelete.Utils.RSAUtils;
import com.example.asuredelete.aop.EXCTime;
import com.google.common.base.Preconditions;
import it.unisa.dia.gas.jpbc.Element;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TianUpload {

    @SneakyThrows
    @EXCTime
    public Element upload(GK gk,String filePath){
        Preconditions.checkArgument(gk!=null,"gk不能为空");
        String rsaPK=KeyManger.getRsaPK();
        List<String> bCK = gk.getBCK();


        List<Element> rList=new ArrayList<>();
        List<Element> randList = addRandom(bCK, rsaPK, rList);
        //未加随机数的解密
        List<String> decList = KeyManger.decString(bCK);

        List<Element> ckList = removeRandom(decList, rList);
        List<Element> dDK = gk.getDdk();
        AtomicInteger i=new AtomicInteger(0);
        List<Element> dkList = dDK.stream().map(dk -> dk.div(ckList.get(i.getAndIncrement()))).collect(Collectors.toList());
        Element dk = Lagrange.PloyReco(dkList);
        //加密
        File file=new File(filePath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        Element fe = FuncUtils.getPairing().getG1().newElementFromBytes(bytes);
        return fe.mul(dk);


    }

    public List<Element> addRandom(List<String> bCK,String rsaPK,List<Element> rList){
       return bCK.stream().map(ck -> {
            Element ri = FuncUtils.getRandomFromG1();
            rList.add(ri);
            String rsi = RSAUtils.encryptByPublicKey(ri.toBytes(), rsaPK);
            Element ckti = FuncUtils.getPairing().getG1().newElementFromBytes(ck.getBytes());
            Element rti = FuncUtils.getPairing().getG1().newElementFromBytes(rsi.getBytes());
            return ckti.mul(rti);
        }).collect(Collectors.toList());

    }

    public List<Element> removeRandom(List<String> bCK,List<Element> rList){
        AtomicInteger i=new AtomicInteger(0);
       return bCK.stream().map(ck->{
            Element cki = FuncUtils.getPairing().getG1().newElementFromBytes(ck.getBytes());
            return cki.div(rList.get(i.getAndIncrement()));
        }).collect(Collectors.toList());
    }


}
