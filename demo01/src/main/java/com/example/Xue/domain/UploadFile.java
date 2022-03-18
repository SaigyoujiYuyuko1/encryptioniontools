package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

@Data
public class UploadFile {
    String fileName;
    Integer index;
    XueCT ct;
    Element rou;
    Element AAI;
    byte[] sign;
}
