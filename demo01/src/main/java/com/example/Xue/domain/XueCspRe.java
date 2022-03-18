package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

@Data
public class XueCspRe {
    Element x;
    Element rou;
    Integer index;
    Element omiga;
    byte[] sign;
}
