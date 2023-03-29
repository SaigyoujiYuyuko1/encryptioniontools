package com.example.DDSD.service;

import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;

public class Solution {

    public static void main(String[] args) {
        Element a = FuncUtils.getRandomFromZp();
        Element b = FuncUtils.getRandomFromZp();
        Element g = FuncUtils.getRandomFromG1();
        Element c = a.powZn(b);
        g.powZn(c);
    }
}


