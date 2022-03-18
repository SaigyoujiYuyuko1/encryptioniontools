package com.example.demo02;

import java.util.Scanner;

public class AliTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);//构建一个Scanner对象
        String s1 = sc.next(); // 接收一个单词，空格分隔
        String s2 = sc.nextLine(); // 接收一行字符串
        sc.close(); //关闭Scanner对象

    }

}
