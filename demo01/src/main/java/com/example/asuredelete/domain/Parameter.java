package com.example.asuredelete.domain;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {
    private Field g1;
    private Field zp;


    private Element g;
    private Element alpha;
    private Element beta;
    private Element h;
}
