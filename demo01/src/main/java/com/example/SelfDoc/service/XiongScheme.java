package com.example.SelfDoc.service;

import com.example.SelfDoc.domain.XiongCT;
import com.example.SelfDoc.domain.XiongMSK;
import com.example.SelfDoc.domain.XiongPk;
import com.example.asuredelete.Utils.SizeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XiongScheme {
    @Autowired
    private  XiongSetup xiongSetup;
    @Autowired
    private XiongEncrypt xiongEncrypt;
    @Autowired
    private XiongDecrypt xiongDecrypt;

    public  void XiongDemo(){
        String filePath="D:\\Desktop\\琐碎\\ab.pdf";
        String policy="(( 1 and 2) and (3 or 4 or 5) )";
        int attNum=25;
        XiongPk pk=new XiongPk();
        XiongMSK msk=new XiongMSK();
        xiongSetup.setup(msk,pk,attNum);
        XiongCT ct = xiongEncrypt.encrypt(pk, msk, filePath, policy, attNum/5);
        xiongDecrypt.decrypt(ct,pk,filePath,attNum/2);
        String size = SizeUtils.getSize( pk.toString().getBytes().length+msk.toString().getBytes().length);
        System.out.println("KGC存储开销："+size);

    }
}
