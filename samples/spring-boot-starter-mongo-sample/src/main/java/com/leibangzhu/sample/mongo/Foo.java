package com.leibangzhu.sample.mongo;

import com.leibangzhu.starters.mongo.IMongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Foo {

    @Autowired
    private IMongoClient mongoClient;

    public void addDocument(){
        Document doc = new Document();
        doc.append("name","john").append("age",18);
        mongoClient.insertOne("users",doc);
    }

    public List<Document> find(){
        Document doc = new Document("name","john");
        return mongoClient.find("users",doc);
    }

}
