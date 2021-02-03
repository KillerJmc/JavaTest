package com.test.newfeatures.lambda.practice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    /**
     * 交易员
     */
    record Trader(String name, String city) {}

    /**
     * 交易
     */
    record Transaction(Trader trader, int year, int value) {}

    /**
     * 交易记录
     */
    static List<Transaction> transactions = List.of(
            new Transaction(new Trader("Brian","Cambridge"), 2011, 300),
            new Transaction(new Trader("Raoul", "Cambridge"), 2012, 1000),
            new Transaction(new Trader("Raoul", "Cambridge"), 2011, 400),
            new Transaction(new Trader("Mario","Milan"), 2012, 710),
            new Transaction(new Trader("Mario","Milan"), 2012, 700),
            new Transaction(new Trader("Alan","Cambridge"), 2012, 950)
    );

    public static void main(String[] args) {
        // 1. 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        var l1 = transactions.stream()
                .filter(t -> t.year() == 2011)
                .sorted(Comparator.comparingInt(Transaction::value))
                .collect(Collectors.toList());

        // 2. 交易员都在哪些不同的城市工作过？
        var l2 = transactions.stream()
                .map(t -> t.trader().city())
                .distinct()
                .collect(Collectors.toList());

        // 3. 查找所有来自于剑桥的交易员，并按姓名排序。
        var l3 = transactions.stream()
                .map(Transaction::trader)
                .filter(t -> t.city().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::name))
                .distinct()
                .collect(Collectors.toList());
        // 4. 返回所有交易员的姓名字符串，按字母顺序排序。
        var s4 = transactions.stream()
                // 不支持Transaction::trader::name
                .map(t -> t.trader().name())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
        // 5. 有没有交易员是在米兰工作的？
        var b5 = transactions.stream()
                .anyMatch(t -> t.trader().city().equals("Milan"));
        // 6. 打印生活在剑桥的交易员的所有交易额。
        transactions.stream()
                .filter(t -> t.trader().city().equals("Cambridge"))
                .forEach(t -> System.out.println(t.value));

        // 7. 所有交易中，最高的交易额是多少？
        int max7 = transactions.stream()
                .map(Transaction::value)
                .max(Comparator.naturalOrder())
                .orElse(-1);

        // 8. 找到交易额最小的交易。
        var minTransaction8 = transactions.stream()
                .min(Comparator.comparingInt(Transaction::value))
                .orElse(null);

        // 9. 将交易员封装成key为姓名，value为城市的集合。
        var map9 = transactions.stream()
                .map(Transaction::trader)
                .distinct()
                .collect(Collectors.toMap(Trader::name, Trader::city));

    }}
