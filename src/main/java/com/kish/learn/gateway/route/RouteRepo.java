package com.kish.learn.gateway.route;

import com.kish.learn.gateway.route.model.RoutesItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepo extends MongoRepository<RoutesItem,String> {
}
