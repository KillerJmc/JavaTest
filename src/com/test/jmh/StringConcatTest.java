package com.test.jmh;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

// 基准测试模式为平均时间（非必选，默认为吞吐量模式，即单位时间内方法的吞吐量）
@BenchmarkMode(Mode.AverageTime)
// 状态为多个线程访问一个单例（如果有访问成员变量必须要写）
@State(value = Scope.Benchmark)
// 输出的时间单位（非必选，默认用*10-k s表示）
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// 预热轮数和每次执行时间，时间单位（非必选，默认5轮，每轮10s）
@Warmup(iterations = 3, time = 1)
// 测量时执行轮数和每次执行的时间，时间单位（非必选，默认5轮，每轮10s）
@Measurement(iterations = 3, time = 1)
// 上述测试进行次数（非必选，默认5轮）
@Fork(1)
public class StringConcatTest {
    // 设置成员变量测试时赋予的值
    @Param(value = {"10", "50", "100"})
    private int length;

    // 测试方法标记
    @Benchmark
    public void testStringAdd(Blackhole blackhole) {
        String a = "";
        for (int i = 0; i < length; i++) {
            a += i;
        }
        // 防止因为存在未使用的变量而导致编译时被jvm优化删除所有相关代码
        blackhole.consume(a);
    }

    @Benchmark
    public void testStringBuilderAdd(Blackhole blackhole) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i);
        }
        blackhole.consume(sb);
    }

    public static void main(String[] args) throws RunnerException {
        // 开始测试
        new Runner(new OptionsBuilder().build()).run();
    }
}

