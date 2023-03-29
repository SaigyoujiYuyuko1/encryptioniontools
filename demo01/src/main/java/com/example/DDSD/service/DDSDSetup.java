package com.example.DDSD.service;

import com.example.DDSD.domain.DDMSK;
import com.example.DDSD.domain.DDPK;
import com.example.DDSD.domain.TreeNode;
import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.aop.EXCTime;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class DDSDSetup {

    @Autowired
    private SKGen skGen;

    /**
     *
     * @param pk
     * @param msk
     * @param n 叶子节点数
     */
    @EXCTime
    public void setup(DDPK pk, DDMSK msk, int n){
        Element alpha = FuncUtils.getRandomFromZp();
        Element beta = FuncUtils.getRandomFromZp();
        Element g = FuncUtils.getRandomFromG1();
        msk.setBeta(beta);
        msk.setGAlpha(g.mulZn(alpha));
        msk.setLeafNum(n);
        TreeNode root = skGen.buildTree(new TreeNode(), 2*n, 1);

     LinkedList<TreeNode> path=new LinkedList<>();
        searchNode(root,path,4 );
        //levelOrder(root);
        msk.setRoot(root);
        //System.out.println("root"+root.val);
        //
        pk.setG(g);
        pk.setH(g.powZn(beta));
        pk.setF(g.powZn(FuncUtils.getOneFromZp().div(beta)));
        pk.setEggAlpha(FuncUtils.getPairing().pairing(g,g).powZn(alpha));
        List<Element> vlist=new ArrayList<>();
        for (int i = 0; i <= Math.log(n)/Math.log(2); i++) {
            vlist.add(FuncUtils.getRandomFromZp());
        }

        pk.setVlist(vlist);
        //System.out.println(vlist.size());
    }
    public void levelOrder(TreeNode root) {

        List<List<Integer>> result=new ArrayList<>();
        level(root,1,result);
        for (List<Integer> list : result) {
            for (Integer i : list) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }

    private void level(TreeNode root,int le,List<List<Integer>> result){
        if(root==null) {
            return;
        }
        if(result.size()<le) {
            result.add(new ArrayList<>());
        }

        result.get(le-1).add(root.val);


        if(root.left!=null) {
            level(root.left,le+1,result);
        }
        if(root.right!=null) {
            level(root.right,le+1,result);
        }
    }
    public  boolean searchNode(TreeNode root,LinkedList<TreeNode> s, int target) {
        if(root == null) {
            return false;
        }
        s.push(root);
        if(root.val == target) {
            return true;
        }
        boolean b = false;
        //先去左子树找
        if(root.left != null) {
            b = searchNode(root.left,s,target);
        }
        //左子树找不到并且右子树不为空的情况下才去找
        if(!b && root.right != null) {
            b = searchNode(root.right,s,target);
        }
        //左右都找不到，弹出栈顶元素
        if(!b) {
            s.pop();
        }
        return b;
    }


}
