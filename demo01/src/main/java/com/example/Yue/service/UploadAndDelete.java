package com.example.Yue.service;

import com.example.Yue.doamin.YueMSK;
import com.example.Yue.doamin.YuePK;
import com.example.Yue.doamin.YueSK;
import com.example.Yue.doamin.YueTDR;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

@Service
public class UploadAndDelete {
    @EXCTime
    public YueTDR upload(YueMSK msk,YuePK pk,YueSK sk, String filePath, int ctNum, int time){
        YueTDR yueTDR = tdrGen(msk, filePath, ctNum, time);
        if(!verify(yueTDR,msk,pk,filePath,ctNum ,time)){
            return null;
        }
        return yueTDR;


    }

    private YueTDR tdrGen(YueMSK msk, String filePath, int ctNum, int time){
        YueTDR tdr=new YueTDR();
        tdr.setUserName("user");
        tdr.setFileName(filePath);
        StringBuilder sb=new StringBuilder();
        sb.append(filePath);
        sb.append(time);
        for (int i = 0; i < ctNum; i++) {
            sb.append(i);
        }
        Element tag = FuncUtils.hashFromStringToG1(sb.toString());
        tdr.setTag(tag);
        tdr.setSig(tag.powZn(msk.getR()));
        return tdr;

    }
    private boolean verify(YueTDR tdr, YueMSK msk, YuePK pk,String filePath, int ctNum, int time){
        Element v1 = FuncUtils.getPairing().pairing(tdr.getTag(), msk.getGr());
        Element v2 = FuncUtils.getPairing().pairing(tdr.getSig(), pk.getG());
        StringBuilder sb=new StringBuilder();
        sb.append(filePath);
        sb.append(time);
        for (int i = 0; i < ctNum; i++) {
            sb.append(i);
        }
        Element tagNew = FuncUtils.hashFromStringToG1(sb.toString());
        if(v1.isEqual(v2)&&tagNew.isEqual(tdr.getTag())){
            return true;
        }
        return false;

    }
}
