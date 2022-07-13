package com.ddcode.es.dao;

import com.ddcode.es.po.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends ElasticsearchRepository<User, String> {

    User findByName(String name);

    List<User> findBySex(String sex);

}
