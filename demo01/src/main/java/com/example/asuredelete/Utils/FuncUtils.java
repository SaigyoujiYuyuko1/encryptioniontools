package com.example.asuredelete.Utils;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class FuncUtils {
    public static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static Pairing pairing = PairingFactory.getPairing("assets/a.properties");


    public static Pairing getPairing() {
        return pairing;
    }





    //G1中获取随机元素，获取1，获取0
    public static Element getRandomFromG1() {
        return pairing.getG1().newRandomElement().getImmutable();
    }

    public static Element getOneFromG1() {
        return pairing.getG1().newOneElement().getImmutable();
    }

    public static Element getZeroFromG1() {
        return pairing.getG1().newZeroElement().getImmutable();
    }

    //Zp中获取随机元素，获取1，获取0
    public static Element getRandomFromZp() {
        return pairing.getZr().newRandomElement().getImmutable();
    }

    public static Element getOneFromZp() {
        return pairing.getZr().newOneElement().getImmutable();
    }

    public static Element getZeroFromZp() {
        return pairing.getZr().newZeroElement().getImmutable();
    }

    //H1,H2 : {0, 1}∗ → G1
    public static Element hashFromStringToG1(String str) {
        return pairing.getG1().newElement().setFromHash(str.getBytes(), 0, str.length()).getImmutable();
    }

    public static Element hashFromBytesToG1(byte[] bytes) {
        return pairing.getG1().newElement().setFromHash(bytes, 0, bytes.length).getImmutable();
    }

    //H : {0, 1}∗ → Zp
    public static Element hashFromStringToZp(String str) {
        return pairing.getZr().newElement().setFromHash(str.getBytes(), 0, str.length()).getImmutable();
    }

    public static Element hashFromBytesToZp(byte[] bytes) {
        return pairing.getZr().newElement().setFromHash(bytes, 0, bytes.length).getImmutable();
    }

    //h : G1 → Zp
    @Deprecated
    public static Element hashFromG1ToZp(Element g1_element) {
        // h(y) : G1 -> Zp
        byte[] g1_bytes = g1_element.getImmutable().toCanonicalRepresentation();
        byte[] zp_bytes = g1_bytes;
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-512");
            zp_bytes = hasher.digest(g1_bytes);   //先把G1元素hash成512bits
        } catch (Exception e) {
            e.printStackTrace();
        }
        //再把hash后的bits映射到Zp
        Element hash_result = pairing.getZr().newElementFromHash(zp_bytes, 0, zp_bytes.length).getImmutable();
        return hash_result;
    }

    //{0,1}* -> key space of πkey  int空间
    public static int h1_pai_key(String data) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            byte[] result = hasher.digest(data.getBytes());
            ByteBuffer wrapped = ByteBuffer.wrap(result);
            return wrapped.getShort();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //伪随机置换 pseudorandom permutation πkey() 用于随机选择哪些块进行抽查
    public static List<Integer> pseudoPerm(int key, int n, int c) {
        List<Integer> result = new ArrayList<Integer>(c);
        if (c < n) {
            List<Integer> list = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                list.add(i);
            }
            for (int i = 0; i < key; i++) {
                java.util.Collections.shuffle(list);
            }
            for (int i = 0; i < c; i++) {
                result.add(list.get(i));
            }
        } else {
            System.out.println(" pseudorandom permutation error!");
        }
        return result;
    }

    //{0,1}* -> key space of fkey 字符串空间
    public static String h2_f_key(String data) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-512");
            byte[] result = hasher.digest(data.getBytes());
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    //伪随机函数 pseudorandom function fkey():{0,1}* -> Zp
    public static Element pseudoFunc(String key, int id) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-512");
            byte[] hash_bytes = hasher.digest((key + id).getBytes());   //先把G1元素hash成512bits
            return pairing.getZr().newElementFromHash(hash_bytes, 0, hash_bytes.length).getImmutable();
        } catch (Exception e) {
            e.printStackTrace();
            return pairing.getZr().newRandomElement();
        }
    }



}