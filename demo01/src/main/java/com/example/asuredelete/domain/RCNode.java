package com.example.asuredelete.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.Map;
@Data
public class RCNode {
    String root;
    Element secret;
    Map<String, Element> stringElementMap;

}
