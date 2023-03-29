package com.example.Xue.Service;

import com.example.Xue.domain.XuePK;
import com.example.Xue.domain.XueSK;
import com.example.Xue.domain.UploadFile;
import com.example.Xue.domain.XueCT;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.MerkleTrees;
import com.example.asuredelete.Utils.RSASign;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class XueEncrypt {

    @Autowired
    private MerkleTrees merkleTrees;

    @SneakyThrows
    @EXCTime
    public UploadFile encrypt(XuePK xuePk, XueSK xueSk, int policyNum, String filePath){
        File file=new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Element m = FuncUtils.getPairing().getGT().newElementFromBytes(fileBytes);
        Element s = FuncUtils.getRandomFromZp();
        Element c1= xuePk.getY().powZn(s.duplicate()).mul(m);
        Element c2= xuePk.getG().powZn(s);
        List<List<Element>> tij = xuePk.getTij();
        List<List<Element>> res=new ArrayList<>();
        List<Element> mthList=new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < policyNum; i++) {
            List<Element> temp=new ArrayList<>();
            for (Element e : tij.get(i)) {
                Element t = e.powZn(s);
                temp.add(t);
                mthList.add(t);
            }
            res.add(temp);
        }
        long end = System.currentTimeMillis();
        log.info("计算属性时间：{}",end-start);
        XueCT ct=new XueCT();
        ct.setC1(c1);
        ct.setC2(c2);
        ct.setC3(res);
        long start1 = System.currentTimeMillis();
        Element mthRoot = merkleTrees.merkle_tree(mthList);
        long end1 = System.currentTimeMillis();
        log.info("默克尔哈系树生成时间：{}",end1-start1);
        byte[] signFile = RSASign.signFile(xueSk.getSsk(), mthRoot.toBytes());
        long end2 = System.currentTimeMillis();
        log.info("签名时间：{}",end2-end1);
        StringBuilder sb=new StringBuilder();
        sb.append(filePath);
        //待改
       int index = (int)(Math.random() * policyNum);
        Element x = res.get(index).get(0);
        sb.append(index);
        sb.append(x.toString());
        Element hash = FuncUtils.hashFromStringToG1(sb.toString());
        Element rou = hash.powZn(xueSk.getAlpha());


        UploadFile up=new UploadFile();
        up.setAAI(x);
        up.setCt(ct);
        up.setFileName(filePath);
        up.setRou(rou);
        up.setSign(signFile);
        up.setIndex(index);


        return up;
    }
}
