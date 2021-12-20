package com.example.demo02.service;

import com.example.demo02.Utils.AES;
import com.example.demo02.domain.Cipher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Slf4j
@Service
public class CtOperateService {
    private byte[] ct;

    public void enc(Cipher cipher, SecretKey secretKey ){
        try {
            long s = System.currentTimeMillis();
            byte[] encrypt = AES.encrypt(cipher.getFile(), secretKey);
            long e = System.currentTimeMillis();
            ct=encrypt;
            cipher.setEnctime(e-s);
        } catch (Exception e) {
           log.info("加密出错,e={}",e);
        }
    }

    public void dec(Cipher cipher, SecretKey secretKey ){
        try {
            long s = System.currentTimeMillis();
            String decrypt = AES.decrypt(ct, secretKey);
            long e = System.currentTimeMillis();
            cipher.setDectime(e-s);
        } catch (Exception e) {
            log.info("解密出错,e={}",e);
        }
    }
}
