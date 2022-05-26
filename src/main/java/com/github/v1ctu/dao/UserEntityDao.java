package com.github.v1ctu.dao;

import com.github.v1ctu.database.MongoDB;
import com.github.v1ctu.entity.UserEntity;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class UserEntityDao {

    private final MongoCollection<UserEntity> userCollection;

    public UserEntityDao(MongoDB mongoDB) {
        this.userCollection = mongoDB.getCollection("users", UserEntity.class);
    }

    public void insert(UserEntity user) {
        try {
            userCollection.insertOne(user);
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
    }

    public Collection<UserEntity> find() {
        Set<UserEntity> userEntities = new HashSet<>();
        for (UserEntity userEntity : userCollection.find()) {
            userEntities.add(userEntity);
        }
        return userEntities;
    }

    public UserEntity find(UUID uuid) {
        return userCollection.find(eq("uuid", uuid)).first();
    }

    public void replace(UserEntity user) {
        try {
            userCollection.replaceOne(eq("uuid", user.getUuid()), user, new ReplaceOptions().upsert(true));
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
    }

}
