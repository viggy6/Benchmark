package com.mytoolbase.JavaBE.service;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;

import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mytoolbase.JavaBE.entity.CityInspections;

import com.mytoolbase.JavaBE.entity.People;
import com.mytoolbase.JavaBE.entity.Trades;
import com.mytoolbase.JavaBE.entity.Values;
import com.mytoolbase.JavaBE.repository.PeopleRepository;
import com.mytoolbase.JavaBE.samples.repository.CompanyRepository;
import com.mytoolbase.JavaBE.samples.repository.SamplesRepository;
import com.mytoolbase.JavaBE.samples.repository.TradesRepository;
import com.mytoolbase.JavaBE.stocks.repository.ValuesRepository;
import com.mytoolbase.JavaBE.utility.MatrixGeneratorUtil;

@Service
public class PeopleService {

	@Autowired
	PeopleRepository repo;
	@Autowired
	SamplesRepository cityInspRepo;
	@Autowired
	CompanyRepository companyRepo;
	@Autowired
	TradesRepository tradesRepo;
	@Autowired
	ValuesRepository valRepo;

	public void squentialFetch() {
		System.out.println("Started Seq Fetching");
		LocalDateTime start = LocalDateTime.now();
		List<People> people = repo.findAll();
//		System.out.println(people.size());
		List<Trades> trades = tradesRepo.findAll();
//		System.out.println(trades.size());
//		List<Values> values = valRepo.findAll();
//		System.out.println(values.size());
		List<CityInspections> inspection = cityInspRepo.findAll();
//		System.out.println(inspection.size());
		LocalDateTime end = LocalDateTime.now();
		System.out.println(("I/O Fetching Sequentially -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
	}
	public void squentialFetchGC() throws IOException, InterruptedException {
		System.out.println("Started Seq Fetching");
		LocalDateTime start = LocalDateTime.now();
		List<People> people = repo.findAll();
//		System.out.println(people.size());
		people=null;
		//triggerFullGC();
		List<Trades> trades = tradesRepo.findAll();
//		System.out.println(trades.size());
		trades=null;
		//triggerFullGC();
		List<Values> values = valRepo.findAll();
//		System.out.println(values.size());
		values=null;
		//triggerFullGC();
	
		List<CityInspections> inspection = cityInspRepo.findAll();
//		System.out.println(inspection.size());
		inspection=null;
		LocalDateTime end = LocalDateTime.now();
		triggerFullGC();
		System.out.println(("I/O Fetching Sequentially -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
	}

	public void parallellFetch() {
		System.out.println("Started Parllel Fetching");
		LocalDateTime start = LocalDateTime.now();
		ExecutorService e1 = Executors.newFixedThreadPool(4);
		
		try {

			Future<?> f1 = e1.submit(() -> {
				List<People> people = repo.findAll();
//				System.out.println(people.size());
			});

			Future<?> f2 = e1.submit(() -> {
				List<Trades> trades = tradesRepo.findAll();
//				System.out.println(trades.size());
			});

			Future<?> f3 = e1.submit(() -> {
				//List<Values> values = valRepo.findAll();
//				System.out.println(values.size());
			});
			Future<?> f4 = e1.submit(() -> {
				List<CityInspections> inspections = cityInspRepo.findAll();
//				System.out.println(inspections.size());
			});

			f1.get();
			f2.get();
			f3.get();
			f4.get();
			LocalDateTime end = LocalDateTime.now();

			System.out.println(("I/O Fetching Parallelly -->")
					+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			e1.shutdown();
		}
	}

	private long seqFibonacci(int n) {
		if (n == 0 || n == 1) {
			return 1;
		}
		return seqFibonacci(n - 1) + seqFibonacci(n - 2);
	}

	private Long parFibonacci(int n) {
		if (n == 0 || n == 1) {
			return 1l;
		}
		try {
			ExecutorService e1 = Executors.newFixedThreadPool(2);
			Future<Long> f1 = e1.submit(() -> {
				return parFibonacci(n - 1);
			});
			Future<Long> f2 = e1.submit(() -> {
				return parFibonacci(n - 2);
			});
			return f1.get() + f2.get();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}
		return -1l;
	}

	public static int[][] multiply(int[][] matrix1, int[][] matrix2) {
		LocalDateTime start = LocalDateTime.now();
		int resultRows = matrix1.length;
		int resultColumns = matrix2[0].length;

		int[][] result = new int[resultRows][resultColumns];

		int columns2 = matrix2[0].length;

		for (int i = 0; i < resultRows; i++) {
			for (int j = 0; j < columns2; j++) {
				result[i][j] = 0;
				for (int k = 0; k < resultColumns; k++) {
					result[i][j] += matrix1[i][k] * matrix2[k][j];
				}
			}
		}
		LocalDateTime end = LocalDateTime.now();
		System.out.println(("Matrix Mult Sequentially -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		return result;

	}

	public static int[][] multiplyPar(int[][] matrix1, int[][] matrix2)
			throws InterruptedException, ExecutionException {
		LocalDateTime start = LocalDateTime.now();
		int resultRows = matrix1.length;
		int resultColumns = matrix2[0].length;

		int[][] result = new int[resultRows][resultColumns];

		int columns2 = matrix2[0].length;
		ExecutorService e1 = Executors.newCachedThreadPool();
		ArrayList<ThreadMultiplier> ops = new ArrayList();
		for (int i = 0; i < resultRows; i++) {
			ops.add(new ThreadMultiplier(result, matrix1, matrix2, i));
		}

		List<Future<Boolean>> f = e1.invokeAll(ops);
		for (Future<Boolean> cf : f) {
			cf.get();
		}
		e1.shutdown();
		LocalDateTime end = LocalDateTime.now();

		System.out.println(("Matrix Mult Parallelly -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		return result;

	}

	public void goodParallelIteration() {
		List<People> people = repo.findAll();
		LocalDateTime start = LocalDateTime.now();
		for (People p : people) {
			for (int i = 0; i < 100000; i++) {

			}
		}
		LocalDateTime end = LocalDateTime.now();
		System.out.println(("Iteration Seq Legacy -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		start = LocalDateTime.now();
		people.forEach(p -> {
//			IntStream.range(0, 100000).forEach(i->{});

			for (int i = 0; i < 100000; i++) {

			}
		});
		end = LocalDateTime.now();
		System.out.println(("Iteration Seq Stream -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		start = LocalDateTime.now();
		people.parallelStream().forEach(p -> {
			for (int i = 0; i < 100000; i++) {

			}
		});
		end = LocalDateTime.now();
		System.out.println(("Iteration Parllel Stream -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
	}

	public void badParallelIteration() {
		List<People> people = repo.findAll();
		LocalDateTime start = LocalDateTime.now();
		for (People p : people) {
			for (int i = 0; i < 100000; i++) {

			}
		}
		LocalDateTime end = LocalDateTime.now();
		System.out.println(("Iteration Seq Legacy -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		start = LocalDateTime.now();
		people.forEach(p -> {
			IntStream.range(0, 100000).forEach(i -> {
			});

//			for (int i = 0; i < 100000; i++) {
//
//			}
		});
		end = LocalDateTime.now();
		System.out.println(("Iteration Seq Stream -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		start = LocalDateTime.now();
		people.parallelStream().forEach(p -> {
			IntStream.range(0, 100000).parallel().forEach(i -> {
			});
		});
		end = LocalDateTime.now();
		System.out.println(("Iteration Parllel Stream -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
	}

	public void dontUse() {
		long sum = 0;
		LocalDateTime start = LocalDateTime.now();
		for (int i = 0; i < 1000000000; i++) {
			sum += i;
		}
		LocalDateTime end = LocalDateTime.now();
		System.out
				.println(("Sum Seq  -->") + (end.get(ChronoField.MILLI_OF_DAY) - start.get(ChronoField.MILLI_OF_DAY)));
		start = LocalDateTime.now();
		LongStream.range(0, 1000000000).reduce(sum, (subsum, n) -> {
			return subsum += n;
		});
		end = LocalDateTime.now();
		System.out.println(
				("Sum Stream Seq  -->") + (end.get(ChronoField.MILLI_OF_DAY) - start.get(ChronoField.MILLI_OF_DAY)));

		start = LocalDateTime.now();
		LongStream.range(0, 1000000000).parallel().reduce(sum, (subsum, n) -> {
			return subsum += n;
		});
		end = LocalDateTime.now();
		System.out.println(
				("Sum Stream Par  -->") + (end.get(ChronoField.MILLI_OF_DAY) - start.get(ChronoField.MILLI_OF_DAY)));
		AtomicLong ASum = new AtomicLong(0);
		
	
		var wrapper = new Object() {
			Long value=(long) 0;
		};
		
		start = LocalDateTime.now();
		LongStream.range(0, 1000000000).parallel().forEach(i -> {

			//ASum.getAndAdd(i);
			wrapper.value=wrapper.value+i;
		});
		end = LocalDateTime.now();
		System.out.println(
				("Sum Stream Par Bad -->") + (end.get(ChronoField.MILLI_OF_DAY) - start.get(ChronoField.MILLI_OF_DAY)));

	}

	public void benchmark() throws InterruptedException, ExecutionException, IOException {
		// TODO Auto-generated method stub
// I/O Based Parallellism		
//		this.squentialFetch();
//		this.parallellFetch();
//		this.squentialFetchGC();
// Compute Based Parallellism	
//		int[][] m1 = MatrixGeneratorUtil.generateMatrix(2000, 2000);
//		int[][] m2 = MatrixGeneratorUtil.generateMatrix(2000, 2000);
//
//		multiply(m1, m2);
//		multiplyPar(m1, m2);

//Negatives of bad parallellism	
		LocalDateTime start = LocalDateTime.now();
		seqFibonacci(50);
		LocalDateTime end = LocalDateTime.now();
		System.out.println(("Fib Sequentially -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));
		
		 start = LocalDateTime.now();
		parFibonacci(50);
		end = LocalDateTime.now();
		System.out.println(("Fib Sequentially -->")
				+ (end.get(ChronoField.SECOND_OF_DAY) - start.get(ChronoField.SECOND_OF_DAY)));

//Iteration Examples
//goodParallelIteration();
//badParallelIteration();

	//	dontUse();
		

	}
	public static void triggerFullGC() throws IOException, InterruptedException {
	    String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	    Process process = Runtime.getRuntime().exec(
	            String.format("jmap -histo:live %s", pid)
	    );
	    System.out.println("Process completed with exit code :" + process.waitFor());
	}

}



class ThreadMultiplier implements Callable<Boolean> {

	private final int[][] result;
	private int[][] matrix1;
	private int[][] matrix2;
	private final int row;

	public ThreadMultiplier(int[][] result, int[][] matrix1, int[][] matrix2, int row) {
		this.result = result;
		this.matrix1 = matrix1;
		this.matrix2 = matrix2;
		this.row = row;
	}

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < matrix2[0].length; i++) {
			result[row][i] = 0;
			for (int j = 0; j < matrix1[row].length; j++) {
				result[row][i] += matrix1[row][j] * matrix2[j][i];

			}

		}
		return true;
	}

}
