package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

@Data
public class XueDR {
    String fileName;
    Integer attr;
    Element valid;
    Element inValid;
}
