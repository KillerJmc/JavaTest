package com.test.newfeatures.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class TestNewDateAPI {
	public static void main(String[] args) {
		test01();
		test02();
		test03();
		test04();
		test05();
		test06();
		test07();
		test08();
	}

	//1. LocalDate LocalTime LocalDateTime
	//Immutable 不可改变的
	private static void test01() {
		System.out.println("--------------------------------");
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		
		LocalDateTime ldt2 = LocalDateTime.of(2019, 9, 18, 13, 0);
		System.out.println(ldt2);
		
		LocalDateTime ldt3 = ldt2.plusYears(2);
		System.out.println(ldt3);
		
		LocalDateTime ldt4 = ldt2.minusMonths(2);
		System.out.println(ldt4);
		
		System.out.println(ldt.getYear());
		System.out.println(ldt.getMonthValue());
		System.out.println(ldt.getDayOfMonth());
		System.out.println(ldt.getHour());
		System.out.println(ldt.getMinute());
		System.out.println(ldt.getSecond());
		System.out.println(ldt.getDayOfYear());
		System.out.println("--------------------------------");
	}
	
	//2. Instant : 时间戳(以Unix元年: 1970.1.1 00:00:00 到某个时间的毫秒值)
	private static void test02() {
		Instant ins1 = Instant.now();
		//默认获取UTC时区，和中国时差8小时
		System.out.println(ins1);
		
		OffsetDateTime odt = ins1.atOffset(ZoneOffset.ofHours(8));
		System.out.println(odt);
		
		//获取Unix时间:新纪元时间(Epoch Time)的毫秒值
		//Epoch [ˈepək]
		System.out.println(ins1.toEpochMilli());
		System.out.println(System.currentTimeMillis());
		
		Instant ins2 = Instant.ofEpochSecond(3600);
		System.out.println(ins2);
		System.out.println("--------------------------------");
	}
	
	//Duration: 计算两个时间之间的间隔
	private static void test03() {
		Instant ins1 = Instant.now();
		sleep(1000);
		Instant ins2 = Instant.now();
		
		Duration duration = Duration.between(ins1, ins2);
		System.out.println(duration);
		System.out.println(duration.getSeconds());
		System.out.println(duration.toMillis());
		
		System.out.println("-------");
		
		LocalTime lt1 = LocalTime.now();
		sleep(1000);
		LocalTime lt2 = LocalTime.now();
		System.out.println(Duration.between(lt1, lt2).toMillis());
		System.out.println("--------------------------------");
	}
	
	//Period: 计算两个日期之间的间隔
	private static void test04() {
		LocalDate ld1 = LocalDate.of(2018, 1, 1);
		LocalDate ld2 = LocalDate.now();
		Period period = Period.between(ld1, ld2);
		System.out.println(period);
		System.out.println(period.getYears());
		System.out.println(period.getMonths());
		System.out.println(period.getDays());
		System.out.println("--------------------------------");
	}
	
	//TemporalAdjuster: 时间校正器
	//Temporary 暂时的
	//Temporal 时间的，世间的
	private static void test05() {
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		
		LocalDateTime ldt2 = ldt.withDayOfMonth(9);
		System.out.println(ldt2);
		
		//下一个周日
		LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		System.out.println(ldt3);
		
		LocalDateTime ldt5 = ldt.with(l -> {
			LocalDateTime ldt4 = (LocalDateTime) l;
			DayOfWeek dow = ldt4.getDayOfWeek();
			if (dow.equals(DayOfWeek.FRIDAY)) {
				return ldt4.plusDays(3);
			} else if (dow.equals(DayOfWeek.SATURDAY)) {
				return ldt4.plusDays(2);
			} else {
				return ldt4.plusDays(1);
			}
		});
		
		System.out.println(ldt5);
		System.out.println("--------------------------------");
	} 
	
	//DateTimeFormatter: 格式化时间 / 日期
	private static void test06() {
		DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
		LocalDateTime ldt = LocalDateTime.now();
		String strDate = ldt.format(dtf);
		System.out.println(strDate);
		
		System.out.println("-----");
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
		String strDate2 = ldt.format(dtf2);
		System.out.println(strDate2);
		
		LocalDateTime ldt2 = LocalDateTime.parse(strDate2, dtf2);
		System.out.println(ldt2);
		System.out.println("--------------------------------");
	}
	
	//ZonedDate ZonedTime ZonedDateTime
	private static void test07() {
		Set<String> set = ZoneId.getAvailableZoneIds();
		set.stream()
		   .limit(3)
		   .forEach(System.out::println);
		System.out.println("...");
	}
	private static void test08() {
		LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Ulyanovsk"));
		System.out.println(ldt);
		//相当于LocalDateTime ldt2 = LocalDateTime.now("Asia/Shanghai");
		LocalDateTime ldt2 = LocalDateTime.now();
		//与UTC时间相比有8小时时差
		ZonedDateTime zdt = ldt2.atZone(ZoneId.of("Asia/Shanghai"));
		System.out.println(zdt);
		System.out.println("--------------------------------");
	}
	
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 时间戳转LocalDateTime
	 * long timestamp = System.currentTimeMillis();
	 * Instant instant = Instant.ofEpochMilli(timestamp);
	 * LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	 */

	/**
	 * LocalDateTime转时间戳
	 * LocalDateTime dateTime = LocalDateTime.now();
	 * dateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
	 * dateTime.toInstant(ZoneOffset.of("+08:00")).toEpochMilli();
	 * dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	 */

}



