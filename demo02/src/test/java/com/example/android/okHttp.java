package com.example.android;

import com.example.Demo02Application;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

public class okHttp extends Demo02Application {
    private OkHttpClient okHttpClient;
    @Test
    public void fun1(){
        Long a=10L;
        String s = a.toString();
        System.out.println(s);

    }

}
