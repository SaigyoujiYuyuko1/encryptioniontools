package com.example.asuredelete.service;

import com.example.asuredelete.aop.EXCTime;
import com.example.asuredelete.domain.*;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CSAD {
    @Autowired
    private Encrypt encrypt;
    @Autowired
    private DeleteImpl delete;
    @Autowired
    private DeleteRequest deleteRequest;
    @Autowired
    private Setup setup;
    @Autowired
    private ProofCheck proofCheck;


    String access_policy_example_1 = "0 and 1 and (2 or 3)";
    String access_policy_example_2 = "(( 1 and 2) and (3 or 4 or 5) and (6 and 7 and (8 or 9 or 10)))";
    String access_policy_example_3 = "(1 and 2) and (3 or 4 or 5)";
    String access_policy_example_4 = "( 1 or 2) and (3 or 4 or 5)";
    String access_policy_example_5 = "( 1 and 2) and (3 and 4 or 5)";
    String filePath="D:\\Desktop\\琐碎\\ab.pdf";
    int policyNum=14;

    @EXCTime
    public void CSADDemo(){
        int num=7;
        long t0 = System.currentTimeMillis();
        List<String> policy =new ArrayList<>();
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_1);







        try{
            long t1 = System.currentTimeMillis();
            Parameter pp = setup.setupPP();
            long t12 = System.currentTimeMillis();
            PK pk = setup.setupPK(pp);
            MSK msk = setup.setupMSK(pp);
            SK sk = setup.ketGen(msk, pk, pp, policyNum);
            long t2 = System.currentTimeMillis();
            CT ct = encrypt.encFile(pp,pk, policy, num,filePath);
            long t3 = System.currentTimeMillis();

            DR dr = deleteRequest.delReq(pp, msk, pk, num);
            long t4 = System.currentTimeMillis();
            List<Element> list = delete.delImpl(pp, ct, dr);
            long t5 = System.currentTimeMillis();
            Element proof = delete.proofGen(pp, list);
            long t6 = System.currentTimeMillis();
            Boolean judge = proofCheck.verifyProof(pp, proof);

            long t7= System.currentTimeMillis();
            System.out.println("-------------CSAD-------------");
        log.info("CSAD初始化时间：{}",t12-t1);
        log.info("CSAD密钥生成时间：{}",t2-t12);
        log.info("CSAD加密时间：{}",t3-t2);
        log.info("CSAD提出删除请求时间：{}",t4-t3);
        log.info("CSAD执行删除操作时间：{}",t5-t4);
        log.info("CSAD生成证据时间：{}",t6-t5);
        log.info("CSAD中CSP删除总时间：{}",t6-t4);
        log.info("CSAD用户验证时间：{}",t7-t6);
        log.info("我的总共时间：{}",t7-t0);
        }catch (Exception e){log.info("",e);}



    }
}
