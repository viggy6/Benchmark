package com.mytoolbase.JavaBE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@Configuration
@EnableMongoRepositories(basePackages = "com.mytoolbase.JavaBE.stocks.repository",mongoTemplateRef = "mongoStocksTemplate")

public class MongoConfig3 {
	
	@Value("${spring.data-mongo.stocks.uri}")
	String uri;

	@Bean(name="mongoStocksTemplate")
	
	public MongoTemplate template() {
		return new MongoTemplate(factory());
	}
	
	public MongoDatabaseFactory factory() {
		return new SimpleMongoClientDatabaseFactory(uri);
	}
	
}
