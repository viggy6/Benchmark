package com.mytoolbase.JavaBE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.ConnectionString;



@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.mytoolbase.JavaBE.reactiverepo",reactiveMongoTemplateRef = "mongoReactTemplate")

public class ReactiveConfig {
	
	@Value("${spring.data-mongo.uri}")
	String uri;

	@Bean(name="mongoReactTemplate")
	
	public ReactiveMongoTemplate template() {
		return new ReactiveMongoTemplate(factory());
	}
	
	public  ReactiveMongoDatabaseFactory factory() {
		ConnectionString cs = new ConnectionString(uri);
		
		return new SimpleReactiveMongoDatabaseFactory(cs);
	}
	
}
