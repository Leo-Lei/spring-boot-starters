package com.leibangzhu.starters.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoClient implements IMongoClient {

    @Override
    public long deleteOne(String db, String collection, Document document) {
        return getCollection(db,collection).deleteOne(document).getDeletedCount();
    }

    @Override
    public long deleteMany(String db, String collection, Document document) {
        return getCollection(db,collection).deleteMany(document).getDeletedCount();
    }

    @Override
    public void insertOne(String db, String collection, Document document) {
        getCollection(db,collection).insertOne(document);
    }

    @Override
    public void insertMany(String db, String collection, List<Document> documents) {
        getCollection(db,collection).insertMany(documents);
    }

    @Override
    public List<Document> find(String db, String collection, Document query) {
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
    public List<Document> find(String db, String collection, Document query, int limit) {
        MongoCursor<Document> cursor = getCollection(db,collection).find(query).limit(limit).iterator();
        List<Document> documents = new ArrayList<>();
        while (cursor.hasNext()) {
            documents.add(cursor.next());
        }
        return documents;
    }

    @Override
    public long updateOne(String db, String collection, Document query, Document update) {
        return getCollection(db,collection).updateOne(query, update).getModifiedCount();
    }

    @Override
    public long count(String db, String collection, Document query) {
        return getCollection(db, collection).count(query);
    }

    private MongoCollection<Document> getCollection(String dbName, String collectionName){
        return mongo.getDatabase(dbName).getCollection(collectionName);
    }

    private com.mongodb.MongoClient mongo;

    public MongoClient(com.mongodb.MongoClient mongo){
        this.mongo = mongo;
    }
}
