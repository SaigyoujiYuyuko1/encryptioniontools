package com.example.Xue.Service;

import com.example.Xue.domain.ReKey;
import com.example.Xue.domain.XueCT;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XueVerify {
    @Autowired
    private ReEncrypt reEncrypt;

    public boolean verify(XueCT ct, ReKey rk,Element proof){
        Element root = reEncrypt.reEnc(ct, rk);
        if(proof.equals(root)){
            return true;
        }
        return false;
    }
}
