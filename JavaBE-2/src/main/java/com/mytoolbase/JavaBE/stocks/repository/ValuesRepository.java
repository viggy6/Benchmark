package com.mytoolbase.JavaBE.stocks.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mytoolbase.JavaBE.entity.Values;

public interface ValuesRepository extends MongoRepository<Values, String>{

}
