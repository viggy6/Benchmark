package com.mytoolbase.JavaBE.samples.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mytoolbase.JavaBE.entity.Trades;

public interface TradesRepository extends MongoRepository<Trades, String> {

}
