package com.mytoolbase.JavaBE.samples.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mytoolbase.JavaBE.entity.Companies;

public interface CompanyRepository extends MongoRepository<Companies, String>{

}
