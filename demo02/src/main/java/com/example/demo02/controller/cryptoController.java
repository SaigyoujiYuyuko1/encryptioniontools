package com.example.demo02.controller;

import com.example.demo02.Utils.AES;
import com.example.demo02.domain.Cipher;
import com.example.demo02.service.CtOperateService;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.print.attribute.standard.Media;
import java.io.File;

@RestController
@RequestMapping(value = "/crypto")
public class cryptoController {
    @Autowired
    private CtOperateService ctOperateService;
    @GetMapping(produces =  "application/json;charset=UTF-8")
    public Cipher ctOpe(){
        Cipher cipher=new Cipher();
        cipher.setFile("aswdscxdfghjklsscxdscxdfghjklsdsfwefxdffghjklsdsfwefxdfdsfscxdfghjklsdsfscxdfghjklsdsfwefxdfwefxdfwscxdfghjklsdsfwefxdfefxdfdsdf");
        try {
            SecretKey secretKey = AES.generateKey();

            ctOperateService.enc( cipher,secretKey);
            ctOperateService.dec(cipher,secretKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
        cipher.setDectime(10);

        return cipher;
    }


    @GetMapping(value="2",produces =  "application/json;charset=UTF-8")
    public String ctOpe2(){
        Cipher cipher=new Cipher();
        cipher.setFile("asdfqertyuiopasdfghjkqertyuiopasdfqertyuiopasdfghjklghjkllmnbvqertyuiopasdfghjklcxesfdse");
        try {
            SecretKey secretKey = AES.generateKey();

            ctOperateService.enc( cipher,secretKey);
            ctOperateService.dec(cipher,secretKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject json=new JsonObject();
        json.addProperty("enctime",cipher.getEnctime());
        json.addProperty("dectime",cipher.getDectime());

        return json.toString();
    }

    @PostMapping(value = "/upload",consumes ="application/json",produces =  "application/json;charset=UTF-8")
    public String upload(@RequestBody Cipher cp){
        System.out.println(cp.getFile());
        System.out.println(cp.getEnctime());
        System.out.println(cp.getDectime());
        return null;
    }
}
