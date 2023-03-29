package com.example.za;

import java.util.HashMap;
import java.util.Map;

public class AmwayFamily

{  

    public static void main (String args[])   

    {  

        System.out.println(100 + 100 +"Simplilearn");   

        System.out.println("Amway Company" + 100 + 100);  

    }
    public void judge(String s){
        int len=s.length();
        Map<Character, Integer> map=new HashMap<>();

        for (int i = 0; i < len; i++) {
            Character c=s.charAt(i);
            if(map.containsKey(c)){
                map.put(c,map.get(c)+1);
            }else {
                map.put(c,1);
            }
        }
        map.forEach((key, value) -> {
            if(value>1){
            System.out.println(key+":"+value);
            }
        });
    }

}