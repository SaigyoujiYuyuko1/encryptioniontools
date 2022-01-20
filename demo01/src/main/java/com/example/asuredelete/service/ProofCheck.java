package com.example.asuredelete.service;

import com.example.asuredelete.domain.BigComplex;
import com.example.asuredelete.domain.Parameter;
import it.unisa.dia.gas.jpbc.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProofCheck {
    @Autowired
    private BigDFastTransfer bigDFastTransfer;
    public void verifyProof(Parameter pp,Element proof){
        int lim = BigDFastTransfer.lim;
        BigComplex[] aYrays = Encrypt.aYrays;
        BigComplex[] bYrays = DeleteRequest.bYrays;
        BigComplex[] resCof=new BigComplex[bigDFastTransfer.MAX];
            for(int i = 0;i <= lim;++i) {
                resCof[i] =BigComplex.Mul( aYrays[i] , bYrays[i]);
            }

        BigComplex[] cof = bigDFastTransfer.FFT(resCof, -1);
    }
}
