package com.example.asuredelete.service;

import com.example.asuredelete.Utils.FuncUtils;
import com.example.asuredelete.Utils.SizeUtils;
import it.unisa.dia.gas.jpbc.Element;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class StorageCost {
    @SneakyThrows
    public static void main(String[] args) {
        int attnum=5;
        String filePath="D:\\Desktop\\琐碎\\ab.pdf";
        List<Element> ct=new ArrayList<>();


        File file=new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Element m = FuncUtils.getPairing().getGT().newElementFromBytes(fileBytes);
        ct.add(m);

                Element g = FuncUtils.getRandomFromG1();
                Element a = FuncUtils.getRandomFromZp();
                Element  b= FuncUtils.getRandomFromZp();
        for (int i = 0; i < attnum; i++) {
            for (int j = 0; j < 10; j++) {
                ct.add((g.powZn(a)).powZn(b));
            }

        }
        System.out.println(SizeUtils.getSize(ct.toString().getBytes().length));
    }
}
