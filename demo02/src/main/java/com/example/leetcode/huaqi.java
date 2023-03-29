package com.example.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class huaqi {
    public static void main(String[] args) {

    }
    public int minCostWithTimeLimited (int maxTime, int[][] edges, int[] passingFees) {
        // write code here
       int inf=Integer.MAX_VALUE;
       int len=passingFees.length;
       int[][] dp=new int[maxTime+1][len];
        for (int i = 0; i < maxTime+1; i++) {
            Arrays.fill(dp[i],inf);
        }
        dp[0][0]=passingFees[0];
        for (int i = 0; i < maxTime; i++) {
            for (int j = 0; j < edges.length; j++) {
                int m=edges[j][0],n=edges[j][1],k=edges[j][2];
                if(k<=i){
                    dp[i][m]=Math.min(dp[i-k][n]+passingFees[m],dp[i][m]);
                    dp[i][n]=Math.min(dp[i-k][m]+passingFees[n],dp[i][n]);
                }
            }
        }
        int ans=inf;
        for (int i = 0; i < maxTime; i++) {
            ans=Math.min(dp[i][len-1],ans);
        }
        if(ans==inf){
            return -1;
        }
        return ans;

    }
    public String merge (int[] nums1, int m, int[] nums2, int n) {
        // write code here
        List<Integer> res=new ArrayList<>();
        for (int i = 0; i < m; i++) {
            res.add(nums1[i]);
        }
        for (int i = 0; i < n; i++) {
            res.add(nums2[i]);
        }
        Collections.sort(res);
        for (int i = 0; i < nums1.length; i++) {
            nums1[i]=res.get(i);
            System.out.print(nums1[i]);
        }
        StringBuilder sb=new StringBuilder();
        for (Integer re : res) {
            sb.append(re+"");
        }
        return sb.toString();
    }
}
