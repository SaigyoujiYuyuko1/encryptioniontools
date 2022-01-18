package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.access.AccessControlEngine;
import com.example.asuredelete.access.AccessControlParameter;
import com.example.asuredelete.access.parser.ParserUtils;
import com.example.asuredelete.access.tree.AccessTreeEngine;
import com.example.asuredelete.domain.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Encrypt {
    @Autowired
    private AccessTreeEngine accessTreeEngine;
    @Autowired
    private  BigDFastTransfer bigDFastTransfer;


    private Pairing pairing = FuncUtils.pairing;
    private BigDecimal zero=BigDecimal.ZERO;
    private BigDecimal one=BigDecimal.ONE;
    int MAX = 1024;

    
    public static  BigComplex[] xrays ;
    public static  BigComplex[] ploys ;

    /**
     * 根据访问策略产生访问控制树（叶子节点）
     * @param policy 访问策略
     * @return
     */
    public Map<String, Element> genAccessTree(String policy){

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
        return stringElementMap;
    }

    /**
     * 产生根节点孩子节点的参数
     * @param secret rc节点的秘密值（多项式y值）
     * @param num rc节点数量
     * @param policy 与rc节点数量对应的访问策略
     * @return
     */
    public List<RCNode> computeRCNodes(Parameter pp,Element secret,int num,List<String> policy){
        List<RCNode> rcList=new ArrayList<>();
        BigComplex[] ploy=new BigComplex[num];
        ploy[0]=new BigComplex(new BigDecimal(secret.toString()),zero);
        for (int i = 1; i < num; i++) {
            ploy[i]=new BigComplex(new BigDecimal(FuncUtils.getRandomFromZp().toString()),zero);
        }
        BigComplex[] xray = bigDFastTransfer.FFT(ploy, 1);
        BigComplex[] yray = computeY(xray, ploy);
        //全局变量，删除请求时用到
        xrays=xray;
        ploys=ploy;
        Element g = pp.getG();
        for (int i = 0; i < num; i++) {
            RCNode rc=new RCNode();
            rc.setXray(xray[i]);
            rc.setRoot(xray[i].toString());
            rc.setSecret(pairing.getZr().newElement(new BigInteger(yray[i].toString())));
            rc.setGy(g.powZn(rc.getSecret()));
            rc.setStringElementMap(genAccessTree(policy.get(i)));
            rc.setLeafNodes(computeLeafNode(pp,rc));
        }

        return rcList;

    }

    public List<LeafNode> computeLeafNode(Parameter pp,RCNode rc){
        Map<String, Element> map = rc.getStringElementMap();
        Element g = pp.getG();
        List<LeafNode> leafNodes = map.keySet().parallelStream().map(att -> {
            LeafNode leaf = new LeafNode();
            leaf.setCg(g.powZn(map.get(att)));
            leaf.setCy(FuncUtils.hashFromBytesToG1(att.getBytes(StandardCharsets.UTF_8)).powZn(map.get(att)));
            return leaf;
        }).collect(Collectors.toList());
        return leafNodes;
    }

    /**
     * 根据多项式和x坐标计算y坐标(秘密值)
     * @param xray x坐标（单位根）
     * @param ploy 多项式系数集合（有限域中随机数）
     * @return
     */
    public BigComplex[] computeY(BigComplex[] xray,BigComplex[] ploy){
        BigComplex[] yray=new BigComplex[xray.length];
        for (int i = 0; i < xray.length; i++) {
            BigComplex res=new BigComplex(zero,zero);
            for (int j = 0; j < ploy.length; j++) {
                res=BigComplex.Add(res,BigComplex.Mul(ploy[j],xray[i].pow(j)));
            }
            yray[i]=res;
        }
        return yray;
    }

    /**
     * ABE加密算法
     * @param pp
     * @param policy
     * @param num
     */
    @SneakyThrows
    public CT encFile(Parameter pp,List<String> policy,int num){
        File file=new File("D:\\Desktop\\待看\\paper\\DATA SHARING.xlsx");
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        Element g = pp.getG();
        Element alpha = pp.getAlpha();
        Element s = FuncUtils.getRandomFromG1();
        Element filekey = pairing.pairing(g, g).powZn(alpha.mul(s));
        Element cipher = filekey.mul(pairing.getG1().newElementFromBytes(fileBytes));

        Element c = pp.getH().powZn(s);

        List<RCNode> rcNodes = computeRCNodes(pp,alpha, num ,policy);

        CT ct=new CT();
        ct.setCipher(cipher);
        ct.setC(c);
        ct.setRcNodesList(rcNodes);

        return ct;
    }


    public static void main(String[] args) {
        Encrypt en=new Encrypt();
       // List<RCNode> rcNodes = en.computeRCNodes(null, 4);
        Pairing pairing = FuncUtils.getPairing();
        Element rg = FuncUtils.getRandomFromG1();
        Element zp = FuncUtils.getRandomFromZp();
        System.out.println("g元素长度："+rg.toString().length());
        System.out.println(rg.toString());
        System.out.println("zp元素长度："+zp.toString().length());
        System.out.println(zp.toString());
        double dzp = Double.parseDouble(FuncUtils.getRandomFromZp().toString());

        System.out.println(dzp);



    }
}
