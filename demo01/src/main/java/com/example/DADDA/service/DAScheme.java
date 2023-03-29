package com.example.DADDA.service;

import com.example.DADDA.domain.*;
import com.example.asuredelete.Utils.SizeUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DAScheme {
    @Autowired
    private DASetup daSetup;
    @Autowired
    private DAEncrypt daEncrypt;
    @Autowired
    private DAKeyGen daKeyGen;
    @Autowired
    private DADeleteReq daDeleteReq;
    @Autowired
    private DADeleteExec daDeleteExec;
    @Autowired
    private DAVerify daVerify;


    public void dadda(){
        String policy="( 1 and 2) and (3 or 4 or 5) ";
        String filePath="D:\\Desktop\\琐碎\\ab.pdf";
        int num=30;
        DAMSK damsk=new DAMSK();
        DAPK dapk=new DAPK();
        long s = System.currentTimeMillis();
        daSetup.setup(damsk,dapk);
        DASK dask = daKeyGen.keyGen(dapk, damsk, num);
        DACT ct = daEncrypt.encrypt(dapk, num, policy, filePath);
        String size = SizeUtils.getSize(ct.toString().getBytes().length);
        //System.out.println("CSP存储开销："+size);

        DAReq daReq = daDeleteReq.deleteReq(dapk, filePath);
        Element proof = daDeleteExec.deleteExec(daReq, ct);
        boolean res = daVerify.verify(ct, proof);
        long e = System.currentTimeMillis();
        System.out.println("总时间:"+(e-s));

    }
}
