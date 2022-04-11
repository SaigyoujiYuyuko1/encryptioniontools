package com.example.leetcode;


import java.math.BigDecimal;
import java.util.*;


public class TreeNodeUtil {


    public static void main(String[] args) {
       BigDecimal faceValue=new BigDecimal(1000);
       BigDecimal couponRate=new BigDecimal("0.1");
       BigDecimal discountRate=new BigDecimal("0.1");
        int year = 2;
//        BigDecimal money=0;
        BigDecimal m=faceValue.multiply(couponRate);
        BigDecimal res=new BigDecimal(0);
//        System.out.println(discount(couponRate,2,100));
        for (int i = 1; i <= year; i++) {
//            money=faceValue*couponRate;
//            faceValue+=money;
            BigDecimal di = discount(discountRate, i, m);
//            System.out.println(di);
            res=res.add(di);

        }
        BigDecimal benjing=discount(discountRate, year, faceValue);
        System.out.println(res.add(benjing));
        System.out.println("benj"+benjing);
        System.out.println(res.doubleValue());



    }

    public static BigDecimal discount(BigDecimal rate,int year,BigDecimal money){

        BigDecimal div = (rate.add(new BigDecimal(1))).pow(year);

        BigDecimal res = money.divide(div,13,BigDecimal.ROUND_DOWN);

        return res;
    }

    public int func(int i,int j){
        if(i<=0||j<=0){
            return 1;
        }
        return 2*func(i-1,j/2);
    }
    public void tree(){
        String[] nums = {"1", "2", "3", "1", null, "2", null, null, null, null, null, "1", null, null, null};
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]==null){
                nums[i]="-1";
            }
        }
        TreeNode2 root = arrayToTreeNode2(nums);
        List<TreeNode2> list = findDuplicateSubtrees(root);
        list.stream().forEach(p-> {
            System.out.println(p.val);
        });
    }

    int t;
    Map<String, Integer> trees;
    Map<Integer, Integer> count;
    List<TreeNode2> ans;

    public List<TreeNode2> findDuplicateSubtrees(TreeNode2 root) {
        t = 1;
        trees = new HashMap();
        count = new HashMap();
        ans = new ArrayList();
        lookup(root);
        return ans;
    }

    public int lookup(TreeNode2 node) {
        if (node == null) {
            return 0;
        }
        String serial = node.val + "," + lookup(node.left) + "," + lookup(node.right);
        int uid = trees.computeIfAbsent(serial, x-> t++);
        count.put(uid, count.getOrDefault(uid, 0) + 1);
        if (count.get(uid) == 2) {
            ans.add(node);
        }
        return uid;
    }




    public  TreeNode2 arrayToTreeNode2(String[] array){
        if(array.length == 0){
            return null;
        }
        TreeNode2 root = new TreeNode2(array[0]);
        Queue<TreeNode2> queue = new LinkedList<>();
        queue.add(root);
        boolean isLeft = true;
        for(int i = 1; i < array.length; i++){
            TreeNode2 node = queue.peek();
            if(isLeft){
                    node.left = new TreeNode2(array[i]);
                    queue.offer(node.left);

            }else {
                if(array[i] != null){
                    node.right = new TreeNode2(array[i]);
                    queue.offer(node.right);
                }
                queue.poll();
                isLeft = true;
            }
        }
        return root;
    }
}
 class TreeNode2 {
    String val;
    TreeNode2 left;
    TreeNode2 right;

    TreeNode2() {
    }

    TreeNode2(String val) {
        this.val = val;
    }


}
