package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.domain.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeleteRequest {
    @Autowired
    private Encrypt encrypt;
    private Pairing pairing = FuncUtils.pairing;
    private BigDecimal zero=BigDecimal.ZERO;
    public static int mid;
    public static  BigComplex[] bYrays ;
    /**
     *
     * @param msk
     * @param pk
     * @param num 根节点孩子的总属性数量（num>=n）
     */
    public DR delReq(Parameter pp,MSK msk, PK pk, int num){
        BigComplex[] xray = Encrypt.xrays;
        int coflen = Encrypt.coflen;
        BigComplex[] aY = Encrypt.aYrays;
        //计算多项式B(x)
        BigComplex[] ployB=new BigComplex[coflen];
        for (int i = 0; i < coflen; i++) {
            ployB[i]=new BigComplex(new BigDecimal(FuncUtils.getRandomFromZp().toString()),zero);
        }
        BigComplex[] bY = encrypt.computeY(xray, ployB);
            bYrays=bY;
        //掩码操作
        int n=BigDFastTransfer.lim;
        if(n==1){
            while(n <= coflen*2) {
                n<<=1;
                n++;
            }
        }
        if(num>n){
            num=(int)Math.ceil(Math.random() * (coflen));
        }
        mid=num;
        List<Element> listA=new ArrayList<>();
        List<Element> listB=new ArrayList<>();
        Element g = pp.getG();
        for (int i = 0; i < num; i++) {
            Element tempA = pairing.getZr().newElement(new BigInteger(aY[i].toString()));
            Element tempB = pairing.getZr().newElement(new BigInteger(bY[i].toString()));
            /**
             * 多项式A这部分参数在密文中已经出现过，此处为重复计算，不计算时间
             */
            listA.add(g.powZn(tempA));
            Element div = pairing.getZr().newElement(n);
            listB.add(g.powZn(tempB.div(div)));
        }
        for (int i = num; i < n; i++) {
            Element tempA = pairing.getZr().newElement(new BigInteger(aY[i].toString()));
            Element tempB = pairing.getZr().newElement(new BigInteger(bY[i].toString()));
            Element div = pairing.getZr().newElement(n);
            listA.add(g.powZn(tempA.div(div)));
            listB.add(g.powZn(tempB));
        }

        DR dr=new DR();
        dr.setPloyA(listA);
        dr.setPloyB(listB);
        return dr;
    }
}
