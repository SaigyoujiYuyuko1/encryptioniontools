package com.example.Yue.service;

import com.example.Yue.doamin.*;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.MerkleTrees;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeAndDelete {
    @EXCTime
    public YueTP sendAndDelete( YueMSK msk,YueCT ct,String filePath, int time){

        YueTP tp=new YueTP();
        //TA send to CSP
        Element tt= FuncUtils.hashFromStringToG1(time+"").powZn(msk.getDelta());
        //CSP
        YueTD td = ct.getTd();
        Element te = FuncUtils.getPairing().pairing(tt, td.getX());
        Element te2 = FuncUtils.hashFromStringToZp(te.toString());
        td.setY(td.getZ().sub(te2));
        List<Element> list=new ArrayList<>();
        list.add(FuncUtils.hashFromStringToG1("user"));
        list.add(FuncUtils.hashFromStringToG1(filePath));
        list.add(FuncUtils.hashFromStringToG1("A"));
        list.add(FuncUtils.hashFromStringToG1(td.toString()));
        list.add(FuncUtils.hashFromBytesToG1(ct.getC0()));
        list.add(FuncUtils.hashFromStringToG1(ct.getC1().toString()));
        list.add(ct.getC2());
        List<Element> l1 = ct.getC31();
        List<Element> l2 = ct.getC32();
        for (int i = 0; i < l1.size(); i++) {
            Element e1 = l1.get(i);
            Element e2 = l2.get(i);
            list.add(e1.add(e2));
        }
        Element root = MerkleTrees.merkle_tree(list);
        double aiff = Math.ceil(Math.log(list.size()) / Math.log(2));
        List<Element> aai = list.subList(0, (int)aiff+1);

        tp.setUserName("user");
        tp.setFileName(filePath);
        tp.setHr(root);
        tp.setAai(aai);


        return tp;
    }


}
