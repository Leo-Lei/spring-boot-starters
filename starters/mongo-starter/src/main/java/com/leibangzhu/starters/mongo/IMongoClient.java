package com.leibangzhu.starters.mongo;

import org.bson.Document;

import java.util.List;

public interface IMongoClient {

    long deleteOne(String collection,Document document);
    long deleteMany(String collection,Document document);
    void insertOne(String collection,Document document);
    void insertMany(String collection, List<Document> documents);
    List<Document> find(String collection,Document query);
    List<Document> find(String collection,Document query, int limit);
    long updateOne(String collection,Document query,Document update);
    long count(String collection,Document query);
}
