package com.example.SelfDoc.service;

public class Alg {
    /**
     * 给定一个非负整数，你至多可以交换一次数字中的任意两位。返回你能得到的最大值。
     *
     * 示例 1 :
     * 输入: 2736
     * 输出: 7236
     * 解释: 交换数字2和数字7。
     *
     * 示例 2 :
     * 输入: 9973
     * 输出: 9973
     * @param args
     */
    public static void main(String[] args) {



    }
    //4412
    public int exchange(int num){
        String s=num+"";
        int max=-1;
        int ind=0;
        for (int length = s.length(); length > 1; length--) {
            if((s.charAt(length)-'0')>max){
                max=s.charAt(length);
                ind=length;
            }
        }
        if((s.charAt(ind)-'0')<=(s.charAt(0)-'0')){
            String res=s.charAt(0)+s.charAt(ind)+s.substring(2,s.length()-ind+1)+s.charAt(1)+s.substring(s.length()-ind+1,s.length());
            return Integer.valueOf(res);
        }
        if (ind==s.length()){
            return num;
        }
        String res=s.charAt(ind)+s.substring(1,s.length()-ind+1)+s.charAt(0)+s.substring(s.length()-ind+1,s.length());
        return Integer.valueOf(res);
    }
}
