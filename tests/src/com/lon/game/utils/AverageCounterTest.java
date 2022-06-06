package com.lon.game.utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class AverageCounterTest {
    @Test
    public void baseTest() throws InterruptedException {
        AverageCounter collector = new AverageCounter();

        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            TimeUnit.MILLISECONDS.sleep(1);
            collector.update(System.nanoTime() - start);
        }

        System.out.println(collector.getAverageValue() / 1000.f / 1000);
    }

    @Test
    public void nestedTimeCollectorBaseTest() throws InterruptedException {
        AverageCounter collector = new AverageCounter();

        AverageCounter nestedCollector = new AverageCounter();

        for (int i = 1; i < 10; i++) {

            for (int j = 0; j < 1000; j++) {
                long start = System.nanoTime();
                payload(2000000 * i);
                nestedCollector.update(System.nanoTime() - start);
                collector.update(System.nanoTime() - start);
            }

            System.out.printf(
                    "TotalTime avg:\t%f\tTotalTime counter:\t%d\tLevelTime avg:\t%f\tLevelTime counter:\t%d%n",
                    collector.getAverageValue()/1000/1000f,
                    collector.getCounter(),
                    nestedCollector.getAverageValue()/1000/1000f,
                    nestedCollector.getCounter());

            nestedCollector.reset();
        }

    }

    private void payload(long duration) throws InterruptedException {
        TimeUnit.NANOSECONDS.sleep(duration);
    }
}
