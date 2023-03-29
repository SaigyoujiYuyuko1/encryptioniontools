package com.example.DDSD.service;

import com.example.DDSD.domain.DDMSK;
import com.example.DDSD.domain.TreeNode;
import com.example.asuredelete.aop.EXCTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Set;

@Service
public class Revoke {
    @Autowired
    private SKGen skGen;
    @EXCTime
    public Set<TreeNode> revoke(DDMSK msk, Set<TreeNode> rl, int state){
        LinkedList<TreeNode> path=new LinkedList<>();
        skGen.getPathToTarget(msk.getRoot(),(msk.getLeafNum()+state),path);
        for (TreeNode treeNode : path) {
            rl.add(treeNode);
        }
        return rl;
    }
}
