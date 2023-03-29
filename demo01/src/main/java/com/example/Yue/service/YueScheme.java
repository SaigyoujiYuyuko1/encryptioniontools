package com.example.Yue.service;

import com.example.Yue.doamin.*;
import com.example.asuredelete.Utils.SizeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YueScheme {

    @Autowired
    private YueSetup yueSetup;
    @Autowired
    private YueKeyGen yueKeyGen;
    @Autowired
    private YueEncrypt yueEncrypt;
    @Autowired
    private YueDecrypt yueDecrypt;
    @Autowired
    private TimeAndDelete timeAndDelete;
    @Autowired
    private UploadAndDelete uploadAndDelete;
    @Autowired
    private YueVerify yueVerify;

    public void YueDemo(){

        String filePath="D:\\Desktop\\琐碎\\ab.pdf";
        String policy="(( 1 and 2) and (3 or 4 or 5) )";
        int n=4;
        int attNum=5;
        int time=1;

        YuePK pk=new YuePK();
        YueMSK msk=new YueMSK();
        yueSetup.setup(pk,msk);
        YueSK sk = yueKeyGen.keyGen(pk, msk, attNum);
        YueCT ct = yueEncrypt.encrypt(pk, filePath, policy, attNum/5);
        yueDecrypt.decrypt(ct,sk,pk,filePath,attNum/2);
        YueTDR tdr = uploadAndDelete.upload(msk, pk, sk, filePath, attNum + 7, time);
        YueTP tp = timeAndDelete.sendAndDelete( msk, ct, filePath, time);
        yueVerify.verify(tp);
        System.out.println("KGC存储开销："+ SizeUtils.getSize(pk.toString().getBytes().length+msk.toString().getBytes().length+tdr.toString().getBytes().length));

    }
}
