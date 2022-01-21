package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.domain.BigComplex;
import com.example.asuredelete.domain.Parameter;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@Service
public class ProofCheck {
    @Autowired
    private BigDFastTransfer bigDFastTransfer;
    public Boolean verifyProof(Parameter pp,Element proof){
        int lim = BigDFastTransfer.lim;
        BigComplex[] aYrays = Encrypt.aYrays;
        BigComplex[] bYrays = DeleteRequest.bYrays;
        BigComplex[] resCof=new BigComplex[bigDFastTransfer.MAX];
            for(int i = 0;i <= lim;++i) {
                resCof[i] =BigComplex.Mul( aYrays[i] , bYrays[i]);
            }

        BigComplex[] cof = bigDFastTransfer.FFT(resCof, -1);
        for (int i = 1; i < cof.length; i++) {
            cof[0]=BigComplex.Add(cof[0],cof[1]);
        }
        Element pai = FuncUtils.getPairing().getZr().newElement(new BigInteger(cof[0].toString()));
        Element g = pp.getG();
        Element res = g.powZn(pai);
        if(!res.equals(proof)){
            return false;
        }
        return true;
    }
}
