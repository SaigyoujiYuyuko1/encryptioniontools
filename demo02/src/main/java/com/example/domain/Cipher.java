package com.example.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cipher {
    private String file;
//    private byte[]  ct;
    private long enctime;
    private long dectime;
}
