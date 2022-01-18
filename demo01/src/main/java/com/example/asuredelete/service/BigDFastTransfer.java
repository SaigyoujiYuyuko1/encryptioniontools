package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.domain.BigComplex;
import com.example.asuredelete.domain.Complex;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BigDFastTransfer {
    int MAX = 1024;
    double PI =Math.acos(-1);
     static int lim = 1;
    int l;
    int[] r=new int[MAX];
     static List<List<BigInteger>> result=new ArrayList<>();
    BigComplex[] aa=new BigComplex[MAX];
    BigComplex[]bb=new BigComplex[MAX];
    private final static BigDecimal zero=BigDecimal.ZERO;
    private final static BigDecimal one=BigDecimal.ONE;

    public BigComplex[] FFT(BigComplex[] A, int type){
        for(int i = 0;i < lim;++i) {
            if(i < r[i]) {
                BigComplex temp=A[i];
                A[i]=A[r[i]];
                A[r[i]]=temp;
            }
        }
        for(int mid = 1;mid < lim;mid<<=1){
            BigComplex omg=new BigComplex(new BigDecimal(String.valueOf(Math.cos(PI/mid))),
                    new BigDecimal(String.valueOf(type * Math.sin(PI/mid))));
            for(int R = mid<<1,j = 0;j < lim;j += R){
                BigComplex w=new BigComplex(one,zero);
                for(int k = 0;k < mid;++k,w = BigComplex.Mul(w,omg)){
                    BigComplex x = A[j+k],y = BigComplex.Mul(w , A[j + mid + k]);
                    A[j + k] = BigComplex.Add(x,y);
                    A[j + mid + k] = BigComplex.Subtract(x,y);
                }
            }
        }
        return A;
    }

    public void test3(){
        int N=2,M=3;
         final  BigDecimal zero=BigDecimal.ZERO;
        aa[0]=new BigComplex(new BigDecimal(FuncUtils.getRandomFromZp().toString()),zero);
        aa[1]=new BigComplex(new BigDecimal(FuncUtils.getRandomFromZp().toString()),zero);
        bb[0]=new BigComplex(new BigDecimal(FuncUtils.getRandomFromZp().toString()),zero);
        bb[1]=new BigComplex(new BigDecimal(FuncUtils.getRandomFromZp().toString()),zero);
        bb[2]=new BigComplex(new BigDecimal(FuncUtils.getRandomFromZp().toString()),zero);

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
            aa[i] =BigComplex.Mul( aa[i] , bb[i]);
        }
        FFT(aa,-1);
        List<BigInteger> list=new ArrayList<>();
        for(int i = 0;i <= N + M;++i) {
            BigDecimal i1 = aa[i].i;
            if (i1.compareTo(zero)==1) {
            BigDecimal res = (i1.divide(new BigDecimal(lim)));
                BigInteger bigInteger = res.toBigInteger();
                list.add(bigInteger);
//            System.out.println();
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
        BigDFastTransfer fft = new BigDFastTransfer();
        list.parallelStream().forEach(p->{
            fft.test3();
            System.out.println("正在处理："+p);

        });
        System.out.println(result.size());
    }
}
