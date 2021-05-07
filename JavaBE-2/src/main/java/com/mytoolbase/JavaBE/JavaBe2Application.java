package com.mytoolbase.JavaBE;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mytoolbase.JavaBE.service.AlgoDatService;
import com.mytoolbase.JavaBE.service.ParllelStreamsService;
import com.mytoolbase.JavaBE.service.PeopleService;

@SpringBootApplication
public class JavaBe2Application implements CommandLineRunner{

	@Autowired
	private PeopleService service;
	
	@Autowired
	private AlgoDatService datService;
	
	@Autowired
	private ParllelStreamsService parServ;
	
	public static void main(String[] args) {
		SpringApplication.run(JavaBe2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//service.benchmark();
		//datService.benchmark();
		parServ.benchmark();
		
	
		
	}

}
