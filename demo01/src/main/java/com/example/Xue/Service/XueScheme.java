package com.example.Xue.Service;

import com.example.Xue.domain.*;
import com.example.asuredelete.Utils.SizeUtils;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class XueScheme {
    @Autowired
    private XueSetup xueSetup;
    @Autowired
    private XueKenGen xueKenGen;
    @Autowired
    private XueEncrypt xueEncrypt;
    @Autowired
    private XueDeleteReq xueDeleteReq;
    @Autowired
    private ReEncrypt reEncrypt;
    @Autowired
    private XueVerify xueVerify;
    public void XueDemo(){
        long t0 = System.currentTimeMillis();
        XuePK pk=new XuePK();
        XueMSK msk=new XueMSK();
        int num=16;
//        String policy="(( 1 and 2) and (3 or 4 or 5) and (6 and 7 and (8 or 9 or 10) and ((11 or 12 or 13) and 14 and 15))) " +
//                "or ((16 or 17 or 18) and 19 and 20)))";
//        String policy= "0 and 1 and (2 or 3)";
        String policy="( 1 and 2) and (3 or 4 or 5) ";
        String filePath="D:\\Desktop\\琐碎\\ab.pdf";
        xueSetup.setup(pk,msk,num);
        long t1 = System.currentTimeMillis();
        XueSK sk = xueKenGen.keyGen(pk, msk, policy);
        xueKenGen.completeSk(pk,sk);
        long t2 = System.currentTimeMillis();

        UploadFile uploadFile = xueEncrypt.encrypt(pk, sk, num, filePath);
        long t3 = System.currentTimeMillis();
        XueDR dr = xueDeleteReq.genDR(filePath, 1, msk);
        XueCspRe cspRe = xueDeleteReq.cspResponse(pk, 1, uploadFile);
        boolean flag = xueDeleteReq.userVerify(filePath, cspRe, pk, uploadFile.getCt());
//        log.info("用户验证CSP存储数据:{}",flag);
        long t4 = System.currentTimeMillis();
        ReKey reKey = reEncrypt.reEncKeyGen(dr, msk);
        Element proof = reEncrypt.reEnc(uploadFile.getCt(), reKey);
        long t5 = System.currentTimeMillis();
        boolean result = xueVerify.verify(uploadFile.getCt(), reKey, proof);
//        log.info("验证结果：{}",result);
        long t6 = System.currentTimeMillis();
        System.out.println("-------------Xue---------------");
        log.info("Xue初始化时间：{}",t1-t0);
        log.info("Xue密钥生成时间：{}",t2-t1);
        log.info("XueD加密时间：{}",t3-t2);
        log.info("Xue提出删除请求时间：{}",t4-t3);
        log.info("Xue执行删除操作时间：{}",t5-t4);
        log.info("Xue用户验证时间：{}",t6-t5);
        log.info("Xue的总共时间：{}",t6-t0);

        System.out.println("CSP的存储开销："+ SizeUtils.getSize(uploadFile.toString().getBytes().length));
        System.out.println("CSP的文件名："+ SizeUtils.getSize(uploadFile.getFileName().getBytes().length));
        System.out.println("CSP的签名："+ SizeUtils.getSize(uploadFile.getSign().length));
        System.out.println("CSP的ct："+ SizeUtils.getSize(uploadFile.getCt().toString().getBytes().length));
    }
}
