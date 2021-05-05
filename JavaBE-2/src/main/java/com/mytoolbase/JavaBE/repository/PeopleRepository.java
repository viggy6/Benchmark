package com.mytoolbase.JavaBE.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mytoolbase.JavaBE.entity.People;

public interface PeopleRepository extends MongoRepository<People, String>{

}
