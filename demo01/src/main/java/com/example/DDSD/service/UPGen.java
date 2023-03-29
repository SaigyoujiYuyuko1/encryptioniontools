package com.example.DDSD.service;

import com.example.DDSD.domain.*;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class UPGen {
    @Autowired
    private DDSDEncrypt encrypt;

    @Autowired
    private SKGen skGen;


    public DDUP upGen(DDMSK msk,DDPK pk, DDCT ct, Set<TreeNode> rl, int state){
        Element f = ct.getC2t().powZn(skGen.stSave);
        Element flag = encrypt.flag;
        //verify
        if(!f.equals(flag)){
            //System.out.println("验证失败");
        }
        Set<TreeNode> ylist = KUNodes(msk.getRoot(), new TreeNode(state + msk.getLeafNum()));
        LinkedList<TreeNode> path=new LinkedList<>();
        skGen.getPathToTarget(msk.getRoot(),state + msk.getLeafNum()+1,path);
        int index=0;
        for (int i = path.size() - 1; i >= 0; i--) {
            if(ylist.contains(path.get(i))){
                index=path.size()-i-1;
                break;
            }
        }
        //只计算时间，数值不对
        Element stNew=FuncUtils.getOneFromZp();
        List<Element> vlist = pk.getVlist();
        for (int i = index; i < path.size(); i++) {
            if(path.get(i).getVal()%2==1){
                stNew=stNew.mul(vlist.get(i).powZn(path.get(i).getV()));
            }else {
                Element t = path.get(i).getV().mul(FuncUtils.getZeroFromZp());
                stNew=stNew.mul(vlist.get(i).powZn(t));
            }
        }
        DDUP up=new DDUP();
        Element h = pk.getH();
        up.setUp1(h.powZn(stNew.sub(skGen.stSave)));
        up.setUp2(h.powZn(skGen.stSave.sub(stNew)));

        return up;


    }

    public Set<TreeNode> KUNodes(TreeNode root, TreeNode leaf){
        Set<TreeNode> res=new HashSet<>();
        LinkedList<TreeNode> path=new LinkedList<>();
        skGen.getPathToTarget(root,leaf.val,path);
        for (TreeNode treeNode : path) {
            if(treeNode.right!=null){
                res.add(treeNode.right);
            }
        }

        return res;
    }
}
