package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PK {
    Element g;
    Element h;
    Element Y;
    List<List<Element>> Tij;
}
