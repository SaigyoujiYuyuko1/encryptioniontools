package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.access.AccessControlEngine;
import com.example.asuredelete.access.AccessControlParameter;
import com.example.asuredelete.access.parser.ParserUtils;
import com.example.asuredelete.access.tree.AccessTreeEngine;
import com.example.asuredelete.domain.RCNode;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class Encrypt {
    @Autowired
    private AccessTreeEngine accessTreeEngine;
    private Pairing pairing = FuncUtils.pairing;

    public void genAccessTree(String policy){

        int[][] accessPolicy ;
        String[] rhos;
        AccessControlParameter accessControlParameter = null;
        try {
            accessPolicy = ParserUtils.GenerateAccessPolicy(policy);
            rhos = ParserUtils.GenerateRhos(policy);
            accessControlParameter= accessTreeEngine.generateAccessControl(accessPolicy, rhos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //秘密分享到访问树中
        //返回叶子节点属性值-秘密值
        Map<String, Element> stringElementMap = accessTreeEngine.secretSharing(pairing,
                pairing.getZr().newRandomElement(), accessControlParameter);
    }

    public List<RCNode> computeRCNodes(Element secret,int num){
        List<RCNode> rcList=new ArrayList<>();
        Complex[] x=new Complex[num];
        for (int i = 0; i < num; i++) {
            x[i]=new Complex(i,0);
           // x[i]=new Complex(-2*Math.random()+1,0);
        }

        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i].getReal()+"  "+x[i].getImaginary());
        }
        System.out.println("---------------------");
        FastFourierTransformer fft=new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] y= fft.transform(x, TransformType.FORWARD);
        for (int i = 0; i < x.length; i++) {
            System.out.println(y[i].getReal()+"  "+y[i].getImaginary());
        }

        System.out.println("---------------------");
        Complex[] z= fft.transform(y, TransformType.INVERSE);
        for (int i = 0; i < x.length; i++) {
            System.out.println(z[i].getReal()+"  "+z[i].getImaginary());
        }
        return rcList;

    }


    public static void main(String[] args) {
        Encrypt en=new Encrypt();
        List<RCNode> rcNodes = en.computeRCNodes(null, 4);


    }
}
