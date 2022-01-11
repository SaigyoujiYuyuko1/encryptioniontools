package com.example.asuredelete.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.complex.Complex;


@Slf4j
public class FFT {
    private final static double Pi=Math.acos(-1.0);
    int n,m;
    static int l,limit=1;
    static Complex[] a=new Complex[1024];
    static Complex[] b=new Complex[1024];
    static int[] r=new int[1024];

    private static Complex[] fast_fast_tle1(Complex[] arr, int type) {
        for(int i=0;i<limit;i++){
            if(i<r[i]){
                //swap(arr[i],arr[r[i]]);//求出要迭代的序列
               Complex a= arr[i];
               arr[i]=arr[r[i]];
                arr[r[i]]=a;
            }
        }
        //待合并区间的中点
        for(int mid=1;mid<limit;mid<<=1)

        {
            //单位根
            Complex Wn=new Complex( Math.cos(Pi/mid) , type*Math.sin(Pi/mid) );
            //R是区间的右端点，j表示前已经到哪个位置了
            for(int R=mid<<1,j=0;j<limit;j+=R)
            {
                //幂
                Complex w=new Complex(1,0);
                //枚举左半部分
                for(int k=0;k<mid; k++ ,w=w.multiply(Wn))
                {
                    //蝴蝶效应
                    Complex x=arr[j+k];
                    Complex y = new Complex(0, 0);
//                    if(w!=null&&arr[j+mid+k]!=null){
//                        y=arr[j+mid+k].multiply(w);
//                        arr[j+k]=x.add(y);
//                        arr[j+mid+k]=x.subtract(y);
//                    }
                    try {
                        y=arr[j+mid+k].multiply(w);
                        arr[j+k]=x.add(y);
                        arr[j+mid+k]=x.subtract(y);
                    }catch (Exception e){
                        log.error("nullpoint={}:",e);
                    }

                }
            }
        }
        return arr;
    }
   private void fast_fast_tle2(int limit,Complex[] a,int type)
    {
        if(limit==1) {return ;}//只有一个常数项
        Complex[] a1=new Complex[limit>>1];
        Complex[] a2=new Complex[limit>>1];
        for(int i=0;i<=limit;i+=2)//根据下标的奇偶性分类
        {   a1[i>>1]=a[i];
            a2[i>>1]=a[i+1];
        }
        fast_fast_tle2(limit>>1,a1,type);
        fast_fast_tle2(limit>>1,a2,type);
        Complex Wn=new Complex(Math.cos(2.0*Pi/limit) , type*Math.sin(2.0*Pi/limit));
        Complex w=new Complex(1,0);
        //Wn为单位根，w表示幂
        for(int i=0;i<(limit>>1);i++,w=w.multiply(Wn)) {//这里的w相当于公式中的k
            a[i] = a1[i].add(w.multiply(a2[i]));
            a[i + (limit >> 1)] = a1[i].subtract(w.multiply(a2[i]));//利用单位根的性质，O(1)得到另一部分
        }
    }
    public static void main(String[] args) {
        int n=2,m=3;
        int total=m*n;
        System.out.println(total);


        for(int i=1;i<=n;i++){
            a[i]=new Complex(i);
        }
        for(int i=1;i<=m;i++) {
            b[i]=new Complex(i);
        }


        while(limit<=m+n){
            limit=limit<<1;
            l++;
        }
        log.info("limit{} :",limit);
        for(int i=0;i<limit;i++){
            r[i]= ( r[i>>1]>>1 )| ( (i&1)<<(l-1) ) ;
        }
       Complex[] c= fast_fast_tle1(a,1);
        for (int i = 0; i < c.length; i++) {
            System.out.println(c[i].getReal()+"     "+c[i].getImaginary());
        }
        Complex[] d=fast_fast_tle1(b,1);
        for(int i=0;i<=limit;i++) {
            c[i]=c[i].multiply(d[i]);
        }
       Complex[] res= fast_fast_tle1(c,-1);
        for(int i=0;i<=n+m;i++){

            System.out.println((int)(res[i].getReal()/limit+0.5));
        }
    }
}
