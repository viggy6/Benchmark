package com.mytoolbase.JavaBE.reactiverepo;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mytoolbase.JavaBE.entity.People;

import reactor.core.publisher.Flux;

public interface ReactiveRepository extends ReactiveMongoRepository<People, String>{
	 @Query("{ id: { $exists: true }}")
	Flux<List<People>> findMyAll(PageRequest p);

}
