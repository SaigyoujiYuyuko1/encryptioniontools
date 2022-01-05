package com.example.service;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProofGenService {
    Pairing pairing= PairingFactory.getPairing("curves/a.properties");
    public void IDFT(){

        Element a = pairing.getG1().newRandomElement().getImmutable();
        Element b = pairing.getGT().newRandomElement().getImmutable();
        Element c = pairing.getZr().newRandomElement().getImmutable();
        Element d = pairing.getZr().newRandomElement().getImmutable();
        //Element res = pairing.pairing(a, b);
        Element e = a.powZn(c);
        Element f = e.powZn(d);
        Element res2 = pairing.pairing(f, a);
        System.out.println(res2);
    }

    public static void main(String[] args) {
        new ProofGenService().IDFT();
    }
}
