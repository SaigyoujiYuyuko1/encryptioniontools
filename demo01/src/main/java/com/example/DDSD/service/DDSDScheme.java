package com.example.DDSD.service;

import com.example.DDSD.domain.*;
import com.example.asuredelete.Utils.SizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
public class DDSDScheme {
    @Autowired
    private DDSDSetup setup;
    @Autowired
    private SKGen skGen;
    @Autowired
    private DDSDEncrypt encrypt;
    @Autowired
    private Decrypt decrypt;
    @Autowired
    private CTUpdate ctUpdate;
    @Autowired
    private UPGen upGen;
    @Autowired
    private Revoke revoke;
    @Autowired
    private Delay delay;

    public void ddsdDemo(){
        String filePath="D:\\Desktop\\琐碎\\ab.pdf";
        String policy="(( 1 and 2) and (3 or 4 or 5) )";
        DDPK pk=new DDPK();
        DDMSK msk=new DDMSK();
        int n=100;
        int attNum=5;
        int state=1;
        HashSet<TreeNode> rl = new HashSet<>();

        setup.setup(pk,msk,n);
        long s = System.currentTimeMillis();
        DDSK sk = skGen.skGen(msk, pk, attNum, state);
        long s1 = System.currentTimeMillis();
        System.out.println("skGen:"+(s1-s));
        DDCT ct = encrypt.encrypt(pk, filePath, state, policy,attNum/5);
        decrypt.decrypt(ct,sk,attNum/2);
        long s2 = System.currentTimeMillis();
        DDUP up = upGen.upGen(msk, pk, ct, rl, state);
        System.out.println("upGen:"+(System.currentTimeMillis()-s2));
        long s3 = System.currentTimeMillis();
        DDCT ctNew = ctUpdate.update(up, pk, ct);
        System.out.println("CTUpdate:"+(System.currentTimeMillis()-s3));
        revoke.revoke(msk, rl, state);
       delay.delay(pk,msk,ct,rl,n);
        String size =SizeUtils.getSize(msk.toString().getBytes().length+rl.toString().getBytes().length);
        System.out.println("KGC存储开销："+size);


    }
}
