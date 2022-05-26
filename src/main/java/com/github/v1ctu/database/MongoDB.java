package com.github.v1ctu.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.*;

public class MongoDB {

    private final String connectionString;

    private MongoDatabase database;
    private MongoClient mongoClient;

    public MongoDB(String connectionString) {
        this.connectionString = connectionString;
    }

    public void startConnection(String databaseId, String collectionName) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        ConnectionString connection = new ConnectionString(connectionString);

        CodecRegistry pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        mongoClient = MongoClients.create(MongoClientSettings.builder()
            .applyConnectionString(connection)
            .codecRegistry(pojoCodecRegistry)
            .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
            .build());

        database = mongoClient.getDatabase(databaseId);
        if (!hasCollection(collectionName)) database.createCollection(collectionName);
    }

    public MongoCollection<Document> getCollection(String collection) {
        return database.getCollection(collection);
    }

    public <T> MongoCollection<T> getCollection(String collection, Class<T> clazz) {
        return database.getCollection(collection, clazz);
    }

    public void closeConnection() {
        mongoClient.close();
    }

    public boolean hasCollection(final String collectionName) {
        for (final String name : database.listCollectionNames()) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }

        return false;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

}
