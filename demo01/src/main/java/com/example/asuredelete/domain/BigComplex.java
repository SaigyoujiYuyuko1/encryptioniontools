package com.example.asuredelete.domain;


import java.math.BigDecimal;

public class BigComplex {
         public BigDecimal i;
        public BigDecimal j;// 虚数部分
        private final static BigDecimal zero=BigDecimal.ZERO;
        public BigComplex(BigDecimal i, BigDecimal j) {
            this.i = i;
            this.j = j;
        }


        public static BigComplex Add(BigComplex a, BigComplex b) {
            if(a!=null&&b!=null){
            return new BigComplex(a.i .add( b.i), a.j .add( b.j));
            }
            return new BigComplex(zero,zero);
        }

        public static BigComplex Subtract(BigComplex a, BigComplex b) {
            if(a!=null&&b!=null){
            return new BigComplex(a.i .subtract( b.i), a.j .subtract( b.j));
            }
            return new BigComplex(zero,zero);
        }

        public static BigComplex Mul(BigComplex a, BigComplex b) {// 乘法
            if(a!=null&&b!=null){
            return new BigComplex((a.i .multiply( b.i)).subtract(a.j .multiply( b.j)) , (a.i.multiply(b.j)).add(a.j.multiply(b.i)));
            }
            return new BigComplex(zero,zero);
        }

        public static BigComplex GetW(int k, int N) {
            // 欧拉公式求DFT因子
            return new BigComplex(new BigDecimal(Math.cos(-2 * Math.PI * k / N)), new BigDecimal(Math.sin(-2 * Math.PI * k / N)));
        }

        public static BigComplex[] butterfly(BigComplex a, BigComplex b, BigComplex w) {
            //蝶形运算
            return new BigComplex[] { Add(a, Mul(w, b)), Subtract(a, Mul(w, b)) };
        }


}


