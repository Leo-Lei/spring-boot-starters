package com.leibangzhu.starters.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoClient implements IMongoClient {

    @Override
    public long deleteOne(String collection, Document document) {
        return getCollection(db,collection).deleteOne(document).getDeletedCount();
    }

    @Override
    public long deleteMany(String collection, Document document) {
        return getCollection(db,collection).deleteMany(document).getDeletedCount();
    }

    @Override
    public void insertOne(String collection, Document document) {
        getCollection(db,collection).insertOne(document);
    }

    @Override
    public void insertMany(String collection, List<Document> documents) {
        getCollection(db,collection).insertMany(documents);
    }

    @Override
    public List<Document> find(String collection, Document query) {
        MongoCursor<Document> cursor = getCollection(db,collection).find(query).iterator();
        List<Document> documents = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }
        }finally {
            cursor.close();
        }
        return documents;
    }

    @Override
    public List<Document> find(String collection, Document query, int limit) {
        MongoCursor<Document> cursor = getCollection(db,collection).find(query).limit(limit).iterator();
        List<Document> documents = new ArrayList<>();
        while (cursor.hasNext()) {
            documents.add(cursor.next());
        }
        return documents;
    }

    public List<Document> find( String collection, Document query, Document sorts, int pageSize, int pageNum) {
        MongoCursor<Document> cursor;
        if (null == query){
            cursor = getCollection(db,collection).find().sort(sorts).skip((pageNum - 1) * pageSize).limit(pageSize).iterator();
        } else {
            cursor = getCollection(db,collection).find(query).sort(sorts).skip((pageNum - 1) * pageSize).limit(pageSize).iterator();
        }

        List<Document> documents = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }
        }finally {
            cursor.close();
        }

        return documents;
    }

    @Override
    public long updateOne(String collection, Document query, Document update) {
        return getCollection(db,collection).updateOne(query, update).getModifiedCount();
    }

    @Override
    public long count(String collection, Document query) {
        return getCollection(db, collection).count(query);
    }

    private MongoCollection<Document> getCollection(String dbName, String collectionName){
        return mongo.getDatabase(dbName).getCollection(collectionName);
    }

    private com.mongodb.MongoClient mongo;
    private String db;

    public MongoClient(com.mongodb.MongoClient mongo,String db){
        this.mongo = mongo; this.db = db;
    }

}
