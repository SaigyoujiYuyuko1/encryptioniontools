package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

@Data
public class XueSK {
    Element gr;
    Element dw;
    Map<String, Element> leafNodes;
    RSAPrivateKey ssk;
    Element alpha;
}
