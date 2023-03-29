package com.example.demo02;

import java.util.*;

public class MSTest {
    public static int solution1(int num){
        if(num < 1){
            return 0;
        }
        int count = 0;
        for(int i = 1; i != num + 1; i++){
            count += get1Nums(i);
        }
        return count;
    }
    public static int get1Nums(int i){
        int res = 0;
        while(i != 0){
            if(i % 10 == 1){
                res++;
            }
            i /= 10;
        }
        return res;
    }
    public int solution(int[] D, int X) {
        int days = 1, minLevel = D[0], maxLevel = D[0];
        for (int level : D) {
            if ((level < minLevel && maxLevel - level > X) || (level > maxLevel && level - minLevel > X) || (level >= minLevel && level <= maxLevel && (level - minLevel > X || maxLevel - level > X))) {
                ++days;
                minLevel = level;
                maxLevel = level;
            } else {
                if (level < minLevel) {
                    minLevel = level;
                } else if (level > maxLevel) {
                    maxLevel = level;
                }
            }
        }
        return days;
    }
    public int solution(String A, String B) {
        // write your code in Java SE 8
        char[] a = A.toCharArray();
        char[] b = B.toCharArray();
        int res=0;

        for (int i = 0; i < a.length; i++) {
           List<Character> sa=new ArrayList<>();
           List<Character> sb=new ArrayList<>();
            for (int j=i;j<a.length;j++){
                sa.add(a[j]);
                sb.add(b[j]);
                boolean judge = judge(sa, sb);
                if(judge){
                    res++;
                }
            }

        }
        return res;
    }

    public boolean judge( List<Character> sa,List<Character> sb){
        Collections.sort(sa);
        Collections.sort(sb);
        if(!sa.equals(sb)){
            return false;
        }
        return true;
    }
}
