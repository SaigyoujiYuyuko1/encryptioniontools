package com.example.demo02.android;

import com.example.demo02.Demo02Application;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class okHttp extends Demo02Application {
    private OkHttpClient okHttpClient;
    @Test
    public void fun1(){
        Long a=10L;
        String s = a.toString();
        System.out.println(s);

    }

}
