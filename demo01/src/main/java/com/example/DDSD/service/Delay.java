package com.example.DDSD.service;

import com.example.DDSD.domain.*;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class Delay {
    @Autowired
    private  SKGen skGen;
    @Autowired
    private UPGen upGen;
    @Autowired
    private CTUpdate ctUpdate;
    @EXCTime
    public void delay(DDPK pk, DDMSK msk, DDCT ct, Set<TreeNode> rl, int n){
        TreeNode root=null;
        TreeNode rootNew = skGen.buildTree(root, 2*n, 1);
        List<Element> vlist=new ArrayList<>();
        for (int i = 0; i <=n ; i++) {
            vlist.add(FuncUtils.getRandomFromG1());
        }
        pk.setVlist(vlist);
        msk.setLeafNum(n);
        msk.setRoot(rootNew);
        rl.clear();

        DDUP upNew = upGen.upGen(msk, pk, ct, rl, n);
        DDCT update = ctUpdate.update(upNew, pk, ct);



    }
}
