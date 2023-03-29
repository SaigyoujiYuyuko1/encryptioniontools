package com.example.SelfDoc.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.List;

@Data

public class XiongPk {
    //密文分量
    int n;
    int d;
    int u;
    int v;
    Element g;
    Element eggy;
    List<Element> gtl;

}
