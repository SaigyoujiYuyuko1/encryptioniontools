package com.example.za;

import java.util.Arrays;
import java.util.List;

public class Ma {
    public static void main(String[] args) {
        Abstr ab=new Abstr();
StringBuilder sb=new StringBuilder();
        List<Integer> arr= Arrays.asList(3,5,1,4,7,2,3);
        arr.stream().filter(t->t>3).forEach(s->{sb.append(s);});
        System.out.println(ab.getAge());
    }
}
