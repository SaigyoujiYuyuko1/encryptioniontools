package com.example.asuredelete.domain;



import lombok.ToString;
import org.apache.commons.math3.util.FastMath;

import java.math.BigDecimal;

public class BigComplex {
         public BigDecimal i;

    @Override
    public String toString() {
        String t=i+"";
        final String[] split = t.split("\\.");
        return split[0];
    }

    public BigDecimal j;// 虚数部分
    private final transient boolean isNaN;
    private final transient boolean isInfinite;
    public static final BigComplex I = new BigComplex(new BigDecimal(Double.toString(0.0D )),new BigDecimal(Double.toString(1.0D)) );
    public static final BigComplex NaN = new BigComplex(new BigDecimal(Double.toString(0.0D )),new BigDecimal(Double.toString(0.0D )));
    public static final BigComplex INF = new BigComplex(new BigDecimal(Double.toString(1.0D )),new BigDecimal(Double.toString(1.0D )));
    public  boolean isNaN() {
        return this.isNaN;
    }

    public boolean isInfinite() {
        return this.isInfinite;
    }


    private final static BigDecimal zero=BigDecimal.ZERO;
        public BigComplex(BigDecimal i, BigDecimal j) {
            this.i = i;
            this.j = j;
            this.isNaN = Double.isNaN(i.doubleValue()) || Double.isNaN(j.doubleValue());
            this.isInfinite = !this.isNaN && (Double.isInfinite(i.doubleValue()) || Double.isInfinite(j.doubleValue()));

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
        public  double abs() {
        if (this.isNaN) {
            return 0.0D / 0.0;
        } else if (this.isInfinite()) {
            return 1.0D / 0.0;
        } else {
            double q;
            if (FastMath.abs(this.i.doubleValue()) < FastMath.abs(this.j.doubleValue())) {
                if (this.j.doubleValue() == 0.0D) {
                    return FastMath.abs(this.i.doubleValue());
                } else {
                    q = this.i.doubleValue() / this.j.doubleValue();
                    return FastMath.abs(this.j.doubleValue()) * FastMath.sqrt(1.0D + q * q);
                }
            } else if (this.i.doubleValue() == 0.0D) {
                return FastMath.abs(this.j.doubleValue());
            } else {
                q = this.j.doubleValue() / this.i.doubleValue();
                return FastMath.abs(this.i.doubleValue()) * FastMath.sqrt(1.0D + q * q);
            }
        }
    }

        public  BigComplex log() {
            return new BigComplex(new BigDecimal(Double.toString(FastMath.log(this.abs()))), new BigDecimal(Double.toString(FastMath.atan2(this.i.doubleValue(), this.j.doubleValue()))));
        }
        public BigComplex multiply(double factor) {
            if (!this.isNaN && !Double.isNaN(factor)) {
                return !Double.isInfinite(this.i.doubleValue()) && !Double.isInfinite(this.j.doubleValue()) && !Double.isInfinite(factor) ?
                        new BigComplex(new BigDecimal(Double.toString(this.i .doubleValue()* factor)), new BigDecimal(Double.toString(this.j.doubleValue() * factor))) : INF;
            } else {
                return NaN;
            }
        }
        public  BigComplex exp() {
            if (this.isNaN) {
                return NaN;
            } else {
                double expReal = FastMath.exp(this.i.doubleValue());
                return new BigComplex(new BigDecimal(Double.toString(expReal * FastMath.cos(this.j.doubleValue()))),
                        new BigDecimal(Double.toString(expReal * FastMath.sin(this.j.doubleValue()))));
            }
        }

        public  BigComplex pow(double x) {
            return this.log().multiply(x).exp();
        }

}


