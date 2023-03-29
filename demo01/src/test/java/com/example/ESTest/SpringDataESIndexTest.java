//package com.example.ESTest;
//
//import com.example.elasticsearch.dao.ProductDao;
//import com.example.elasticsearch.domain.Product;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringDataESIndexTest {
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
//    @Autowired
//    private ProductDao productDao;
//    //创建索引并增加映射配置
//    @Test
//    public void createIndex(){
//        System.out.println("创建索引");
//    }
//    @Test
//    public void deleteIndex(){
//        //创建索引，系统初始化会自动创建索引
////        boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);
////        System.out.println("删除索引 = " + flg);
//    }
//    @Test
//    public void save1(){
//        Product pro=new Product(1L,"iphone","手机",6200.0,"imgurl");
////        PageRequest.of(0,5, Sort.sort())
//        productDao.save(pro);
//    }
//}
