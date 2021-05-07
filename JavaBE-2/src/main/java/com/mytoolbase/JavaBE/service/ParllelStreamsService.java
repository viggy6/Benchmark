package com.mytoolbase.JavaBE.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mytoolbase.JavaBE.entity.People;
import com.mytoolbase.JavaBE.reactiverepo.ReactiveRepository;
import com.mytoolbase.JavaBE.repository.PeopleRepository;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Service
public class ParllelStreamsService {

	@Autowired
	PeopleRepository repo;
	@Autowired
	ReactiveRepository reactRepo;

	public CompletableFuture<Void> forkJoinWithStreams(ExecutorService e1) throws InterruptedException {

		CompletableFuture<List<People>> future = CompletableFuture.supplyAsync(() -> this.fetch(), e1);
		return future.thenAccept((data) -> {
		});

	}

	public CompletableFuture<Void> forkJoinWithStreams() throws InterruptedException {
		
		

		CompletableFuture<List<People>> future = CompletableFuture.supplyAsync(() -> this.fetch());
		return future.thenAccept((data) -> {
		});

	}

	public Flux<People> forkJoinWithStreamsReactively() throws InterruptedException {
		System.out.println("call");
		return this.fetchReactive();

	}

	public List<People> fetch() {
		System.out.println("call");
		long start = System.currentTimeMillis();
		Page<People> data = repo.findAll(PageRequest.of(0, 10));
		System.out.println(
				"Fetched in--->" + ((System.currentTimeMillis() - start) / 1000.0) + " " + Thread.currentThread());
		return new ArrayList<People>();
	}

	public Flux<People> fetchReactive() {
		return reactRepo.findAll().onBackpressureBuffer(2);
	}

	public void benchmark() throws InterruptedException {
		long start = System.currentTimeMillis();
//		ForkJoinPool e1 = new ForkJoinPool(4);
		Stream<Integer> s = Stream.of(1,2,3);
		
		for (int i = 0; i < 1; i++) {
			//Regular Async Call With Default Pool
//			forkJoinWithStreams()
//			.thenRun(
//					() -> {
//				System.out.println(
//						"Bulk---> " + ((System.currentTimeMillis() - start) / 1000.0));
//			});
			//Regular Async With Custom Fork-Join Pool
//			forkJoinWithStreams(e1)
//			.thenRun(
//					()->{System.out.println("Bulk--->"+((System.currentTimeMillis()-start)/1000.0));
//					});
			//Complete Reactive Programming
//			forkJoinWithStreamsReactively().subscribe(
//					(a)->{System.out.println(a.getName());}, 
//					(b)->{},
//					()->{System.out.println((System.currentTimeMillis()-start)/1000.0);}
//					);

		}
		
		// e1.awaitQuiescence(100000, TimeUnit.MINUTES);
		Thread.sleep(500000);

	}

}
