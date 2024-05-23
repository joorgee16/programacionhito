package com.empresa.programacionhito;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static final String URI = "mongodb+srv://admin:admin@jorge.7del9ci.mongodb.net/";

    public static MongoDatabase connect() {
        MongoClient mongoClient = MongoClients.create(URI);
        MongoDatabase database = mongoClient.getDatabase("actividad17");
        return database;
    }
}
