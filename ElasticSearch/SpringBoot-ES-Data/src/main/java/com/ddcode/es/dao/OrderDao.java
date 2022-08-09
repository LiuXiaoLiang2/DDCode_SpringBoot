package com.ddcode.es.dao;

import com.ddcode.es.po.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends ElasticsearchRepository<Order, String> {
}
