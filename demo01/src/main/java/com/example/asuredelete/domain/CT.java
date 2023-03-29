package com.example.asuredelete.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.List;

@Data
public class CT {
    Element cipher;
    Element C;
    List<RCNode> rcNodesList;
}
