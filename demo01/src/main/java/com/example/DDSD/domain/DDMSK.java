package com.example.DDSD.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

@Data
public class DDMSK {
    Element beta;
    Element gAlpha;
    TreeNode root;
    int leafNum;
}
