package com.example.asuredelete.Utils;

import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;
import java.util.List;

public class Lagrange {
    public static List<Element> PloyDiv(Element alpha, int t, int n){
        List<Element> res=new ArrayList<>();
        List<Element> xishu=new ArrayList<>();
        xishu.add(alpha);
        for (int i = 1; i < t; i++) {
            xishu.add( FuncUtils.getRandomFromG1());
        }
        for (int i = 1; i <= n; i++) {
            Element re=FuncUtils.getZeroFromG1();
            for (int j = 0; j < t; j++) {
                Element temp = xishu.get(j).duplicate();
                int te =(int) Math.pow(i,  j);
                re=re.add(temp.powZn(FuncUtils.getPairing().getZr().newElement(te)));
            }
            res.add(re);
        }
        return res;
    }

    public static Element PloyReco(List<Element> list){
        Element zero=FuncUtils.getZeroFromG1();
        Element y=FuncUtils.getZeroFromG1();
        int len = list.size();
        for (int i = 0; i < len; i++) {
           int t=1;
            for (int j = 0; j < len; j++) {
                if(i==j){
                    j=i+1;
                    if(j>len){
                        break;
                    }
                }
                t*=j/(i-j);
            }
            Element et = FuncUtils.getPairing().getG1().newElement(t);
            y=y.add(et.mul(list.get(i)));
        }
        return y;

    }
    private static double lglrchzh(double X, int n, double x[], double y[]) {
        double Y = 0;
        for (int k = 0; k <= n; k++) {
            double t = 1;
            for (int j = 0; j <= n; j++) {
                if (j == k) {
                    j = k + 1;
                    if (j > n) {
                        break;
                    }
                }
                t *= (X - x[j]) / (x[k] - x[j]);
            }
            Y = Y + t * y[k];
        }
        return Y;
    }

}
