package com.example.jwtAuthentication.repository;

import com.example.jwtAuthentication.entity.Role;
import com.example.jwtAuthentication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepsitory {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User findByEmail(String email){
        Query query  = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    public  User findByRole(Role role){
        Query query = new Query();
        query.addCriteria(Criteria.where("role").is(role));
        return mongoTemplate.findOne(query,User.class);
    }

    public void saveUser(User user){
        mongoTemplate.save(user);
    }



}
