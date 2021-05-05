package com.mytoolbase.JavaBE.samples.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mytoolbase.JavaBE.entity.CityInspections;

public interface SamplesRepository extends MongoRepository<CityInspections, String>{

}
