package com.example.asuredelete.Utils;
 
import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.jce.provider.JCEMac;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Created by andyfeng on 2017/12/20.
 */
@Component
public class MerkleTrees {


 
    /**
     * execute merkle_tree and set root.
     */
    public Element merkle_tree(List<Element> txList) {
 
        List<Element> tempTxList = new ArrayList<Element>();
 
        for (int i = 0; i < txList.size(); i++) {
            tempTxList.add(txList.get(i).add(FuncUtils.getPairing().getG1().newElement(i)));
        }
 
        List<Element> newTxList = getNewTxList(tempTxList);
 
        //执行循环，直到只剩下一个hash值
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }
 
      return newTxList.get(0);
    }
 
    /**
     * return Node Hash List.
     * @param tempTxList
     * @return
     */
    private List<Element> getNewTxList(List<Element> tempTxList) {
 
        List<Element> newTxList = new ArrayList<Element>();
        int index = 0;
        while (index < tempTxList.size()) {
            // left
            Element left = tempTxList.get(index);
            index++;
            // right
            Element right =FuncUtils.getZeroFromG1();
            if (index != tempTxList.size()) {
                right = tempTxList.get(index);
            }
            // sha2 hex value
            Element sha2HexValue = hash2Zp(left,right);
            newTxList.add(sha2HexValue);
            index++;
 
        }
 
        return newTxList;
    }
 


    /**
     * Get Root
     * @return
     */


    private Element hash2Zp(Element left,Element right){

        Element res = left.add(right);
        return FuncUtils.hashFromStringToZp(res.toString());

    }
}