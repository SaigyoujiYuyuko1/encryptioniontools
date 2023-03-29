package com.example.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Func2 {

    public static void main(String[] args) {
        Func2 f2=new Func2();
        f2.myAtoi("-2147483647");

    }
    public void reorderList(ListNode head) {
        ListNode slow=head;
        ListNode fast=head;
        if (slow==null){
            return;
        }
        while (fast.next!=null&&fast.next.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        ListNode newhead=slow.next;
        slow.next=null;

        ListNode h2 = reverseNode(newhead);
        while (h2!=null){
            System.out.println(h2.val);
            ListNode temp=h2;
            h2=h2.next;
            temp.next=head.next;
            head.next=temp;
            head=temp;
        }

    }

    private ListNode reverseNode(ListNode head){
        ListNode dummy=new ListNode(-1);

        ListNode tail=null;
        while (head!=null){
            ListNode temp=head;
            head=head.next;
            temp.next=tail;
            dummy.next=temp;
            tail=temp;


        }
        return dummy.next;
    }

    public List<Integer> rightSideView(TreeNode root) {
        if(root==null){
            return new ArrayList<>();
        }
        List<List<Integer>> lev=new ArrayList<>();
        levelTree(lev,1,root);
        List<Integer> res=new ArrayList<>();
        for (List<Integer> list : lev) {
            res.add(list.get(list.size()-1));

        }
        return res;
    }

    private void levelTree(List<List<Integer>> lev, int depth, TreeNode root) {
        if(root==null){
            return;
        }
        if(lev.size()<depth){
            lev.add(new ArrayList<>());
        }
        lev.get(depth-1).add(root.val);
        levelTree(lev,depth+1,root.left);
        levelTree(lev,depth+1,root.right);
    }

    public int climbStairs(int n) {
        int[] dp=new int[n+1];
        dp[0]=1;
        dp[1]=1;

        for (int i = 2; i <= n; i++) {
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[n];
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (o1,o2)->o1[0]-o2[0]);

        int[][] res=new int[intervals.length][2];
        res[0]=intervals[0];
        int l=0;
        for (int i = 1; i < intervals.length; i++) {
            int left=res[l][0];
            int right=res[l][1];
            if(left<intervals[i][0]&&intervals[i][0]<right){
                res[l][1]=Math.max(right,intervals[i][1]);
                continue;
            }else {
                res[++l]=intervals[i];
            }

        }
        return Arrays.copyOf(res,l+1);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy=new ListNode(-1);
        ListNode tail=dummy;
        int cur=0;
        while (l1!=null||l2!=null){
            int a=l1==null?0:l1.val;
            int b=l2==null?0:l2.val;
            int temp=a+b+cur;
            cur=temp/10;
            tail.next= new ListNode(temp%10);
            tail=tail.next;
            if(l1!=null){
                l1=l1.next;
            }
            if(l2!=null){
                l2=l2.next;
            }
        }
        if(cur>0){
            tail.next=new ListNode(cur);
        }
        return dummy.next;
    }

    public int mySqrt(int x) {
        if(x==0||x==1){
            return x;
        }
        int right=x,left=1;
        while (left<=right){
            int mid=(left+right)/2;
            if(mid<=x/mid&&(mid+1)>x/(mid+1)){
                return mid;
            }else if(mid<x/mid){
                left=mid+1;
            }else if(mid>x/mid){
                right=mid-1;
            }
        }
        return left;
    }

    public int myAtoi(String s) {
        char[] s1 = s.trim().toCharArray();
        int res=0;
        int max=Integer.MAX_VALUE;
        int min=Integer.MIN_VALUE;
        if (s1.length<1||Character.isLetter(s1[0])) {
            return  0;
        }
        char end = 0;
        int flag=1;
        int i=0;
        if(s1[0]=='-'){
             i=1;
             flag=-1;
        }else if(s1[0]=='+'){
            i=1;
        }
        for (; i < s1.length; i++) {
            char c=s1[i];
            if(!Character.isDigit(c)){
                break;
            }
            if(max/10<res||(max/10==res&&(c-'0')>max%10)){
                return max;
            }
            if(min/10>res||(min/10==res&&(c-'0')>-(min%10))){
                return min;
            }
            res=res*10+flag*(c-'0');

        }



        return res;
    }

    public ListNode sortList(ListNode head) {
        if(head==null) {
            return head;
        }
        ListNode slow=head,fast=head;
        while (fast.next!=null&&fast.next.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        ListNode tmp=slow.next;
        slow.next=null;
        ListNode left=sortList(head);
        ListNode right=sortList(tmp);
        //merge两个有序表
        return mergeTwoListNode(left,right);
    }

    private ListNode mergeTwoListNode(ListNode left, ListNode right) {
        ListNode dummy=new ListNode(-1);
        ListNode temp=dummy;
        while (left!=null&&right!=null){
            if(left.val>=right.val){
                temp.next=right;
                right=right.next;
            }else {
                temp.next=left;
                left=left.next;
            }
            temp=temp.next;
        }
        temp.next=left==null?right:left;
        return dummy.next;
    }

}
