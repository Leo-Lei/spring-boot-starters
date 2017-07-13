package com.leibangzhu.starters.mongo;

import org.bson.Document;

import java.util.List;

public interface IMongoClient {

    long deleteOne(String db,String collection,Document document);
    long deleteMany(String db,String collection,Document document);
    void insertOne(String db,String collection,Document document);
    void insertMany(String db, String collection, List<Document> documents);
    List<Document> find(String db,String collection,Document query);
    List<Document> find(String db,String collection,Document query, int limit);
    long updateOne(String db,String collection,Document query,Document update);
    long count(String db,String collection,Document query);
}
