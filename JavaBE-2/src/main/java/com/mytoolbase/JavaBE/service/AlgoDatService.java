package com.mytoolbase.JavaBE.service;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mytoolbase.JavaBE.entity.People;
import com.mytoolbase.JavaBE.repository.PeopleRepository;

@Service
public class AlgoDatService {

	@Autowired
	PeopleRepository repo;
	
	public void loadBulk() {
		long start = System.currentTimeMillis();
		ArrayList<String> names = new ArrayList<String>();
		names.add("Nick");
		names.add("Luke");
		names.add("Dan");
		names.add("Bob");
		names.add("Indira");
		names.add("Hal");
		start = System.currentTimeMillis();
		ArrayList<People> filteredPeople = repo.findByNameIn(names,PageRequest.of(0, 10000));

		System.out.println("Bulk Fetching --> " + (System.currentTimeMillis() - start) / 1000.0);

	}

	public void loadSingle() throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		ArrayList<String> names = new ArrayList<String>();
		names.add("Nick");
		names.add("Luke");
		names.add("Dan");
		names.add("Bob");
		names.add("Indira");
		names.add("Hal");
//		ExecutorService e1 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//		ArrayList<Callable<Boolean>> tasks = new ArrayList();
//		start = System.currentTimeMillis();
//
	names.parallelStream().forEach(n->repo.findByName(n));
//			tasks.add(()->{
			
//				return true;
//			});
//
		
//		List<Future<Boolean>> results = e1.invokeAll(tasks);
//		for(Future r:results) {
//			r.get();
//		}
//		e1.shutdown();
		System.out.println("Single Fetch --> " + (System.currentTimeMillis() - start) / 1000.0);

	}

	public void benchmark() throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		ExecutorService e1 = Executors.newFixedThreadPool(4);
		ArrayList<Callable<Boolean>> tasks = new ArrayList();
		for (int i = 0; i < 1000; i++) {
			tasks.add(() -> {
				//loadBulk();
				loadSingle();
				return true;
			});
		}
		List<Future<Boolean>> results = e1.invokeAll(tasks, 10000000, TimeUnit.HOURS);

		for (Future r : results) {
			r.get();
		}
		e1.shutdown();
		System.out.println((System.currentTimeMillis() - start) / 1000.0);

	}

}
