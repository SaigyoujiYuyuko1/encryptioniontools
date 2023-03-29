package com.example.DADDA.service;

import com.example.DADDA.domain.DAPK;
import com.example.DADDA.domain.DAReq;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

@Service
public class DADeleteReq {
    public DAReq deleteReq(DAPK pk,String fileName){
        DAReq req=new DAReq();
        Element rand = FuncUtils.getRandomFromZp();
        req.setRd(pk.getG().powZn(rand));
        req.setFileName(fileName);


        return req;
    }
}
