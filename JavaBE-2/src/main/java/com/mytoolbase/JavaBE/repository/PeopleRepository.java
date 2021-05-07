package com.mytoolbase.JavaBE.repository;

import java.util.ArrayList;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mytoolbase.JavaBE.entity.People;

public interface PeopleRepository extends MongoRepository<People, String>{

	@Query("{'name':{$in:?0}}")
	public ArrayList<People> findByNameIn(ArrayList<String> names, PageRequest pageRequest);
	
	@Query("{'name':{$in:?0}}")
	public ArrayList<People> findByNameIn(ArrayList<String> names);
	
	public ArrayList<People> findByName(String name);
}
