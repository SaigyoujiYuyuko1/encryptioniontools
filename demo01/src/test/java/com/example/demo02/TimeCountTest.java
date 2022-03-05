package com.example.demo02;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TimeCountTest {
    @SneakyThrows
    public static void main(String[] args) {
        String A="00:00";
        String B="23:59";
        Integer res = count(A, B);
        System.out.println(res);

    }
    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }
    public static Date addDay(Date date, int n) {
                 Calendar cal = Calendar.getInstance();
                 cal.setTime(date);
                 cal.add(Calendar.DATE, n);
                 return cal.getTime();
             }



    public static Integer count (String a,String b ){
        if(a.equals(b)){
            return 0;
        }
        int result=0;
        String[] a1= a.split(":");
        List<Integer> la=new ArrayList<>();
        la.add(Integer.parseInt(a1[0]));
        la.add(Integer.parseInt(a1[1]));
        String[] b1 = b.split(":");
        List<Integer> lb=new ArrayList<>();
        lb.add(Integer.parseInt(b1[0]));
        lb.add(Integer.parseInt(b1[1]));
        if(la.get(0)==lb.get(0)&&la.get(1)<=lb.get(1)){

            result=countQuarter(la.get(1))-countQuarter(lb.get(1))-1;
        }else if(la.get(0)<lb.get(0)){
            result=(lb.get(0)-la.get(0)-1)*4+countQuarter(la.get(1))+countB(lb.get(1));
        }else {
            lb.set(0, lb.get(0)+24);
            result=(lb.get(0)-la.get(0)-1)*4+countQuarter(la.get(1))+countB(lb.get(1));
        }


        return result;
    }

    public static Integer countQuarter(int t){
        if(t==0){
            return 4;
        }
       else if(t<15){return 3;}
       else if(t<30){return 2;}
       else if(t<45){return 1;}
       else {return 0;}
    }
    public static Integer countB(int t){
        if(t<15){
            return 0;
        }
        else if(t<30){return 1;}
        else if(t<45){return 2;}
        else{return 3;}
    }


    public int solution(int[] A) {
        // write your code in Java SE 8

        for (int i = 0; i < A.length; i++) {  //直接选择排序(两重for循环排序)
            for (int j = i + 1; j < A.length; j++) {
                if (A[i] > A[j]) {
                    int temp = A[j];
                    A[j] = A[i];
                    A[i] = temp;
                }
            }
        }

        for (int i = 0; i < A.length-1; i++) {
            int a = A[i];
            int b = A[i+1];
            if(a==b){
                continue;
            }
            if(a>0&&b>(a+1)){
                return a+1;
            }
        }
        return 1;
    }
}
