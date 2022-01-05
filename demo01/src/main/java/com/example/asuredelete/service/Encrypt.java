package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.access.AccessControlEngine;
import com.example.asuredelete.access.AccessControlParameter;
import com.example.asuredelete.access.parser.ParserUtils;
import com.example.asuredelete.access.tree.AccessTreeEngine;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Map<String, Element> stringElementMap = accessTreeEngine.secretSharing(pairing,
                pairing.getZr().newRandomElement(), accessControlParameter);
    }

    public void testAttributes(){

    }
}
