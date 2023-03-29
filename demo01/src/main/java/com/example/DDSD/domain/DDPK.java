package com.example.DDSD.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.List;

@Data
public class DDPK {
    Element g;
    Element h;
    Element f;
    Element eggAlpha;
    List<Element> vlist;
}
