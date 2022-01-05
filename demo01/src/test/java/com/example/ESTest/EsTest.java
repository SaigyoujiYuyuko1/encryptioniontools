package com.example.ESTest;

import com.alibaba.fastjson.JSON;
import com.example.Demo01Application;
import com.example.elasticsearch.domain.Product;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class EsTest extends Demo01Application {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    private RestHighLevelClient client;
    @Test
    public void createIndex(){
        System.out.println("创建索引");
    }
    //创建索引
    @Test
    public void fun1() throws IOException {
        //1.获取对象
        IndicesClient indices = client.indices();
        //2.具体操作
        CreateIndexRequest re = new CreateIndexRequest("hehe");
        //配置映射信息
        String mappings="";
         re.mapping(mappings, XContentType.JSON);
        CreateIndexResponse createIndexResponse = indices.create(re, RequestOptions.DEFAULT);
    }
    @Test
    public void fun2() throws IOException {
        IndicesClient indices = client.indices();
        //2.具体操作
        GetIndexRequest re = new GetIndexRequest("hehe");
        indices.get(re,RequestOptions.DEFAULT);

    }
   //文档操作
    @Test
    public void adddoc() throws IOException {
        Map data=new HashMap();
        data.put("title","huawei");
        IndexRequest re = new IndexRequest("product").id("2").source(data);
        //传入json文件
        Product p=new Product();
        String s = JSON.toJSONString(p);
        new IndexRequest("product").id(p.getId().toString()).source(s,XContentType.JSON);
        IndexResponse response = client.index(re, RequestOptions.DEFAULT);
        System.out.println(response.getId());

    }
    //查询文档
    @Test
    public void querydoc() throws IOException {
        GetRequest re = new GetRequest("product", "1");
        GetResponse res = client.get(re, RequestOptions.DEFAULT);
        DeleteRequest re2 = new DeleteRequest("product", "2");
        client.delete(re2,RequestOptions.DEFAULT);

    }
    //批量操作
    @Test
    public void bulkTest() throws IOException {
        BulkRequest bulk=new BulkRequest();
        //添加操作
        DeleteRequest deleteRequest = new DeleteRequest();
        bulk.add(deleteRequest);
        IndexRequest indexRequest = new IndexRequest("hehe").id("3").source("");
        bulk.add(indexRequest);

        BulkResponse response = client.bulk(bulk, RequestOptions.DEFAULT);
    }
}
