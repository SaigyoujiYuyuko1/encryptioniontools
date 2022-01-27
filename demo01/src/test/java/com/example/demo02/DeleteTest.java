package com.example.demo02;

import com.example.Demo01ApplicationTests;
import com.example.asuredelete.aop.EXCTime;
import com.example.asuredelete.domain.*;
import com.example.asuredelete.service.*;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest  {
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
    String access_policy_example_2 = "((0 and 1 and 2) and (3 or 4 or 5) and (6 and 7 and (8 or 9 or 10 or 11)))";
    String access_policy_example_3 = "(0 and 1 and 2) and (3 or 4 or 5)";
    String access_policy_example_4 = "(0 and 1 or 2) and (3 or 4 or 5)";
    String access_policy_example_5 = "(0 and 1 and 2) and (3 and 4 or 5)";


    @Test
    @EXCTime
    public void deleteTest(){
        List<String> policy =new ArrayList<>();
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_2);
        policy.add(access_policy_example_3);
        policy.add(access_policy_example_4);
        policy.add(access_policy_example_5);

        try{
            long start = System.currentTimeMillis();
            Parameter pp = setup.setupPP();

            MSK msk = setup.setupMSK(pp);
            PK pk = setup.setupPK(pp);
            CT ct = encrypt.encFile(pp,pk, policy, 5);
            DR dr = deleteRequest.delReq(pp, msk, pk, 5);
            List<Element> list = delete.delImpl(pp, ct, dr);
            Element proof = delete.proofGen(pp, list);

            Boolean judge = proofCheck.verifyProof(pp, proof);

            long end = System.currentTimeMillis();
            System.out.println(end-start);
        }catch (Exception e){log.info("",e);}


    }
}
