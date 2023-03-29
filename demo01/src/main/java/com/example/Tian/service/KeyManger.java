package com.example.Tian.service;

import com.example.asuredelete.Utils.RSAUtils;
import com.sun.istack.internal.NotNull;
import it.unisa.dia.gas.jpbc.Element;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class KeyManger {
    private final static Map<String, Object> map = RSAUtils.genKeyPair();
    @NotNull
    public static String getRsaPK(){
        return RSAUtils.getPublicKey(map);
    }

    public static List<String> dec(List<Element> enList){
        String rsaSK = RSAUtils.getPrivateKey(map);
        AtomicInteger i= new AtomicInteger(0);
        return enList.stream().map(en->{
            String s = RSAUtils.decryptByPrivateKey(en.toString(), rsaSK);

            return s;
        }).collect(Collectors.toList());
    }
    public static List<String> decString(List<String> enList){
        String rsaSK = RSAUtils.getPrivateKey(map);
        AtomicInteger i= new AtomicInteger(0);
        return enList.stream().map(en->{
            String s = RSAUtils.decryptByPrivateKey(en, rsaSK);

            return s;
        }).collect(Collectors.toList());
    }
}
