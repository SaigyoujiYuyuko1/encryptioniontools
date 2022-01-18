package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.domain.BigComplex;
import com.example.asuredelete.domain.Complex;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.LangUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class FastTransfer {
    int MAX = 1024;
    double PI =Math.acos(-1);
    int lim = 1;
    int l;
    int[] r=new int[MAX];
    static List<List<BigInteger>> result=new ArrayList<>();
    Complex[] aa=new Complex[MAX];
    Complex[]bb=new Complex[MAX];


    public Complex[] FFT(Complex[] A, int type){
        for(int i = 0;i < lim;++i) {
            if(i < r[i]) {
                Complex temp=A[i];
                A[i]=A[r[i]];
                A[r[i]]=temp;
            }
        }
        for(int mid = 1;mid < lim;mid<<=1){
            Complex omg=new Complex(Math.cos(PI/mid),type * Math.sin(PI/mid));
            for(int R = mid<<1,j = 0;j < lim;j += R){
                Complex w=new Complex(1,0);
                for(int k = 0;k < mid;++k,w = Complex.Mul(w,omg)){
                    Complex x = A[j+k],y = Complex.Mul(w , A[j + mid + k]);
                    A[j + k] = Complex.Add(x,y);
                    A[j + mid + k] = Complex.Subtract(x,y);
                }
            }
        }
        return A;
    }
    public void test1(){
        int N=2,M=3;
        aa[0]=new Complex(1,0);
        aa[1]=new Complex(2,0);
        bb[0]=new Complex(1,0);
        bb[1]=new Complex(2,0);
        bb[2]=new Complex(1,0);

        while(lim <= N + M) {
            lim<<=1;l++;
        }
        for(int i = 0;i < lim;++i) {
            r[i] = (r[i>>1]>>1) | ((i&1)<<(l-1));
        }
        FFT(aa,1);
        FFT(bb,1);
        for(int i = 0;i < lim;++i) {
            System.out.println("aa-"+i+"="+aa[i].i+","+aa[i].j);
            System.out.println("bb-"+i+"="+bb[i].i+","+bb[i].j);

        }
        for(int i = 0;i <= lim;++i) {
            aa[i] =Complex.Mul( aa[i] , bb[i]);
        }
        FFT(aa,-1);
        for(int i = 0;i <= N + M;++i) {
            double i1 = aa[i].i;
            System.out.println((int)(i1/lim+0.5));

        }
    }
    public void test2(){
        int N=2,M=3;
        aa[0]=new Complex(Double.parseDouble(FuncUtils.getRandomFromZp().toString()),0);
        aa[1]=new Complex(Double.parseDouble(FuncUtils.getRandomFromZp().toString()),0);
        bb[0]=new Complex(Double.parseDouble(FuncUtils.getRandomFromZp().toString()),0);
        bb[1]=new Complex(Double.parseDouble(FuncUtils.getRandomFromZp().toString()),0);
        bb[2]=new Complex(Double.parseDouble(FuncUtils.getRandomFromZp().toString()),0);

        while(lim <= N + M) {
            lim<<=1;l++;
        }
        for(int i = 0;i < lim;++i) {
            r[i] = (r[i>>1]>>1) | ((i&1)<<(l-1));
        }
        FFT(aa,1);
        FFT(bb,1);
//        for(int i = 0;i < lim;++i) {
//            System.out.println("aa-"+i+"="+aa[i].i+","+aa[i].j);
//            System.out.println("bb-"+i+"="+bb[i].i+","+bb[i].j);
//
//        }
        for(int i = 0;i <= lim;++i) {
            aa[i] =Complex.Mul( aa[i] , bb[i]);
        }
        FFT(aa,-1);
        List<BigInteger> list=new ArrayList<>();
        for(int i = 0;i <= N + M;++i) {
              double i1 = aa[i].i;
            BigDecimal bd1;
              if(Double.isNaN(i1)){
                  try {
                  bd1=new BigDecimal(Double.toString(i1));
                  }catch(Exception e){continue;}
              }else{
                bd1=new BigDecimal(i1);
              }
           if(bd1.compareTo(BigDecimal.ZERO)==1){
               BigDecimal res = (bd1.divide(new BigDecimal(lim)));
               BigInteger bigInteger = res.toBigInteger();
               System.out.println(bigInteger);
               list.add(bigInteger);

            }
        }
        if(list.size()==4){
            result.add(list);
        }

    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        FastTransfer fft = new FastTransfer();
//        fft.test2();
        list.parallelStream().forEach(p->{
            fft.test2();
            System.out.println("正在处理："+p);
        });
        System.out.println(result.size());
    }
}
