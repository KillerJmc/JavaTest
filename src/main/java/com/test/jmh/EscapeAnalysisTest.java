package com.test.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Jmc
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 10)
@Measurement(iterations = 3, time = 10)
@Fork(1)
public class EscapeAnalysisTest {
    static class Int {
        private final int value;

        public Int(int value) {
            this.value = value;
        }

        public int hash() {
            return value;
        }
    }

    /**
     * VM Options: -Xlog:gc:withEA.gc.log -Xmx30M -Xss180K
     */
    @Benchmark
    public void withEA() {
        Int n = null;
        for (int i = 0; i < 1_000_000_000; i++) {
            n = new Int(i);
        }

        // ensure for loop is executed
        int hash = n.hash();
    }

    /**
     * VM Options: -Xlog:gc:withoutEA.gc.log -Xmx30M -Xss180K -XX:-DoEscapeAnalysis
     */
    @Benchmark
    public void withoutEA() {
        Int n = null;
        for (int i = 0; i < 1_000_000_000; i++) {
            n = new Int(i);
        }

        // ensure for loop is executed
        int hash = n.hash();
    }
}
