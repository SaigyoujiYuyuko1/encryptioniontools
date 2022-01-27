package com.example.asuredelete.controller;

import com.example.asuredelete.domain.*;
import com.example.asuredelete.service.*;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping()
public class AssuredDelController {

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

   public List<ExpTime> result=new ArrayList<>();

    String access_policy_example_1 = "0 and 1 and (2 or 3)";
    String access_policy_example_2 = "((0 and 1 and 2) and (3 or 4 or 5) and (6 and 7 and (8 or 9 or 10 or 11)))";
    String access_policy_example_3 = "(0 and 1 and 2) and (3 or 4 or 5)";
    String access_policy_example_4 = "(0 and 1 or 2) and (3 or 4 or 5)";
    String access_policy_example_5 = "(0 and 1 and 2) and (3 and 4 or 5)";


    @GetMapping("/delete")
    @ResponseBody
    public List<ExpTime> assuredDel(){
        List<String> policy =new ArrayList<>();
        policy.add(access_policy_example_1);
        policy.add(access_policy_example_2);
        policy.add(access_policy_example_3);
        policy.add(access_policy_example_4);
        policy.add(access_policy_example_5);
        ExpTime et=new ExpTime();
        try{
            long start1 = System.currentTimeMillis();
            Parameter pp = setup.setupPP();
            MSK msk = setup.setupMSK(pp);
            PK pk = setup.setupPK(pp);
            long end1 = System.currentTimeMillis();
            System.out.println("系统初始化时间："+(end1-start1));
            et.setName("系统初始化时间：");
            et.setTime(end1-start1);
            result.add(et);


            long start4 = System.currentTimeMillis();
            CT ct = encrypt.encFile(pp, pk,policy, 5);
            long end4 = System.currentTimeMillis();
            System.out.println("加密总时间："+(end4-start4));
            ExpTime et1=new ExpTime();
            et1.setName("加密总时间：");
            et1.setTime(end4-start4);
            result.add(et1);


            long start5 = System.currentTimeMillis();
            DR dr = deleteRequest.delReq(pp, msk, pk, 5);
            long end5 = System.currentTimeMillis();
            System.out.println("提出删除请求时间："+(end5-start5));
            ExpTime et2=new ExpTime();
            et2.setName("提出删除请求时间：");
            et2.setTime(end5-start5);
            result.add(et2);


            long start6 = System.currentTimeMillis();
            List<Element> list = delete.delImpl(pp, ct, dr);
            long end6 = System.currentTimeMillis();
            System.out.println("执行删除操作时间："+(end6-start6));
            ExpTime et3=new ExpTime();
            et3.setName("执行删除操作时间：");
            et3.setTime(end6-start6);
            result.add(et3);



            long start7 = System.currentTimeMillis();
            Element proof = delete.proofGen(pp, list);
            long end7 = System.currentTimeMillis();
            System.out.println("生成删除证据时间："+(end7-start7));
            ExpTime et4=new ExpTime();
            et4.setName("生成删除证据时间：");
            et4.setTime(end7-start7);
            result.add(et4);



            long start8 = System.currentTimeMillis();
            Boolean judge = proofCheck.verifyProof(pp, proof);
            long end8 = System.currentTimeMillis();
            System.out.println("验证删除证据时间："+(end8-start8));
            ExpTime et5=new ExpTime();

            et5.setName("验证删除证据时间：");
            et5.setTime(end8-start8);
            result.add(et5);

        }catch (Exception e){log.info("",e);}


        return result;

    }

}
