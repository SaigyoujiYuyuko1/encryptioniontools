package com.example.DDSD.service;

import com.example.DDSD.domain.DDMSK;
import com.example.DDSD.domain.DDPK;
import com.example.DDSD.domain.DDSK;
import com.example.DDSD.domain.TreeNode;
import com.example.asuredelete.Utils.FuncUtils;
import it.unisa.dia.gas.jpbc.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Service
public class SKGen {
    /**
     *
     * @param msk
     * @param pk
     * @param attNum DU的属性数量
     * @param state 叶子节点从左到右的编号，从1开始
     * @return
     */


    private DDMSK msk;
    public  Element stSave;

    public DDSK skGen(DDMSK mk, DDPK pk, int attNum, int state){
        this.msk=mk;
        DDSK sk=new DDSK();
        Element g = pk.getG();
        List<Element> l1=new ArrayList<>();
        List<Element> l2=new ArrayList<>();
        Element r = FuncUtils.getRandomFromZp();
        for (int i = 0; i < attNum; i++) {
            Element ri = FuncUtils.getRandomFromZp();
            Element hash = FuncUtils.hashFromBytesToG1(("" + i).getBytes()).powZn(ri);
            l1.add((g.powZn(r)).mul(hash));
            l2.add(g.powZn(ri));
        }
        sk.setD31(l1);
        sk.setD31(l2);
        stSave=getSt(pk,state);
        sk.setD2t(pk.getH().powZn(stSave));

        Element gAlpha = msk.getGAlpha();
        Element div = FuncUtils.getOneFromZp().div(msk.getBeta());
        sk.setD1((gAlpha.mul(g.powZn(r))).powZn(div));


        return sk;

    }

    public TreeNode buildTree(TreeNode root, int nums, int index){
        if (index>=nums) {
            return null;
        }
        root = new TreeNode(index);
        root.left = buildTree(root.left, nums, 2*index);
        root.right = buildTree(root.right, nums, 2*index+1);
        return root;
    }
    public  boolean getPathToTarget(TreeNode node, int target, LinkedList<TreeNode> path) {
        if (node == null) {
            return false;
        }

        path.push(node);
        node.setV(FuncUtils.getRandomFromZp());

        if (node.val == target) {
            return true;
        }
        // find in left tree
        if (getPathToTarget(node.left, target, path)) {
            return true;
        }
        // find in right tree
        if (getPathToTarget(node.right, target, path)) {
            return true;
        }

        // this node is not in the path of target
        // cause leftchild rightchild and itself do not have target node
        path.pop();
        return false;
    }

    public List<Element> getBitString(LinkedList<TreeNode> path){
        List<Element> res=new ArrayList<>();
        for (TreeNode treeNode : path) {
            int val = treeNode.getVal();
            Element v = treeNode.getV();
            if(val%2==1){
                res.add(FuncUtils.getOneFromZp().mul(v));
            }else {
                res.add(FuncUtils.getZeroFromZp().mul(v));
            }
        }
        return  res;

    }

    public Element getSt(DDPK pk,int state){
        LinkedList<TreeNode> path=new LinkedList<>();
        getPathToTarget(msk.getRoot(),(state+msk.getLeafNum()),path);
        List<Element> bitlist = getBitString(path);
        List<Element> vlist = pk.getVlist();
        Element st=vlist.get(0);
        for (int i = 1; i < vlist.size(); i++) {
            st=st.mul(vlist.get(i).powZn(bitlist.get(i-1)));
        }
        return st;

    }



}
