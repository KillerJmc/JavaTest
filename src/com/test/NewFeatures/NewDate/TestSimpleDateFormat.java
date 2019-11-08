package com.test.NewFeatures.NewDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class TestSimpleDateFormat {
	public static void main(String[] args) throws Exception {
		previous();
		nowadays();
	}

	private static void previous() throws InterruptedException, ExecutionException {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Callable<Date> task = new Callable<Date>() {
			@Override
			public Date call() throws Exception {
				//线程安全问题
				//return sdf.parse("20190916");
				//加锁后
				return DateFormatThreadLocal.convert("20190916");
			}
		};
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		List<Future<Date>> result = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			result.add(pool.submit(task));
		}
			
		for (var t : result) {
			//get方法会堵塞
			System.out.println(t.get());
		}
		
		pool.shutdown();
	}
	
	private static void nowadays() throws InterruptedException, ExecutionException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		Callable<LocalDate> task = new Callable<LocalDate>() {
			@Override
			public LocalDate call() throws Exception {
				return LocalDate.parse("20190917", dtf);
			}
		};
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		List<Future<LocalDate>> result = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			result.add(pool.submit(task));
		}
			
		for (var t : result) {
			//get方法会堵塞
			System.out.println(t.get());
		}
		
		pool.shutdown();
	}
}





