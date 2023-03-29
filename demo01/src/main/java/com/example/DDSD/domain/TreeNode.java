package com.example.DDSD.domain;

import it.unisa.dia.gas.jpbc.Element;
import lombok.Data;

@Data
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public Element v;
    public TreeNode(){}
    public TreeNode(int val){
        this.val=val;
    }
}
