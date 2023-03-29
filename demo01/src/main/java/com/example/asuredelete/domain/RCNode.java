package com.example.asuredelete.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class RCNode {
    //单位根
    BigComplex xray;
    String root;
    //单位根对应y值
    Element secret;
    Element gy;
    //该节点对应的访问控制树
    Map<String, Element> stringElementMap;
    //
    List<LeafNode> leafNodes;

}
