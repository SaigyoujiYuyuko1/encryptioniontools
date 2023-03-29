package com.example.Yue.doamin;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.List;

@Data
public class YueCT {
    byte[] c0;
    Element c1;
    Element c2;
    List<Element> c31;
    List<Element> c32;
    YueTD td;
}
