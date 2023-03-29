package com.example.Yue.service;

import com.example.Yue.doamin.YueTP;
import com.example.asuredelete.Utils.MerkleTrees;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

@Service
public class YueVerify {
    @EXCTime
    public void verify(YueTP tp){
        Element proof = MerkleTrees.merkle_tree(tp.getAai());
        if(proof.isEqual(tp.getHr())){
            System.out.println();
        }


    }
}
