package com.example.Tian.service;

import com.example.Tian.domain.GK;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.SizeUtils;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TianScheme {
    @Autowired
    private TianSetup tianSetup;
    @Autowired
    private TianUpload tianUpload;
    @Autowired
    private TianDownLoad tianDownLoad;
    @Autowired
    private TianDel tianDel;
    @Autowired
    private TianDelConfirm tianDelConfirm;

    private static int attNum=5;
    String filePath="D:\\Desktop\\琐碎\\ab.pdf";

    public void tianDemo() {
        long s = System.currentTimeMillis();
        List<Element> attTotal = new ArrayList<>();
        List<Element> att = new ArrayList<>();
        List<Element> delatt = new ArrayList<>();
        for (int i = 0; i < attNum; i++) {
            Element a = FuncUtils.getRandomFromG1();
            attTotal.add(a);
            int j = 0;
            if (j < attNum >> 1) {
                att.add(a);
                delatt.add(a);
            }

        }
        delatt.remove(delatt.size() - 1);
        Map<Element, Element> map = new HashMap<>();
        GK gk = tianSetup.Setup(att, 3, attNum, map);
        Element ct = tianUpload.upload(gk, filePath);
        Element file = tianDownLoad.dec(gk, ct, att, map);
        GK gkNew = tianDel.updateKey(gk, delatt);
        tianDelConfirm.confirm(gk, delatt, map);
        long e = System.currentTimeMillis();
        System.out.println(e - s);

        System.out.println("CSP的存储开销："+ SizeUtils.getSize(ct.toString().getBytes().length));
    }
}
