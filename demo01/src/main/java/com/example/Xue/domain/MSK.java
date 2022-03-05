package com.example.Xue.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

import java.util.List;

@Data
public class MSK {
    Element y;
    List<List<Element>> tij;
}
