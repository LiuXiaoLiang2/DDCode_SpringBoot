package com.ddcode.dao;

import com.ddcode.po.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends ReactiveMongoRepository<User, String> {
}
