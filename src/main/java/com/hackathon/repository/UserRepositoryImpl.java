package com.hackathon.repository;

import com.hackathon.entity.User;
import com.hackathon.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int getLevel(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        query.fields().include("currentLevel");
        return mongoTemplate.findOne(query, User.class).getCurrentLevel();
    }

    @Override
    public void createUser(String username) {
        mongoTemplate.save(new User(username, 1));
    }

    @Override
    public int increaseLevel(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        query.fields().include("currentLevel");
        User user = mongoTemplate.findAndModify(query, new Update().inc("currentLevel", 1).set("status", Status.UNDONE), FindAndModifyOptions.options().returnNew(true), User.class);
        return user.getCurrentLevel();
    }
}
