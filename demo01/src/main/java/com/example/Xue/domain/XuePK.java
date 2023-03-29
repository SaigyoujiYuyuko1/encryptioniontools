package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.List;

@Data
public class XuePK {
    Element g;
    Element h;
    Element Y;
    Element gAlpha;
    List<List<Element>> Tij;

}
