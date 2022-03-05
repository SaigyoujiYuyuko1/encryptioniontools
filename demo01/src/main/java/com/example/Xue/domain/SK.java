package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.Map;

@Data
public class SK {
    Element gr;
    Element dw;
    Map<String, Element> leafNodes;
    Element ssk;
    Element alpha;
    Element g_alpha;
}
