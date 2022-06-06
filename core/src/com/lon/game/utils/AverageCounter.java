package com.lon.game.utils;

public class AverageCounter {
    private long averageValue;
    private long entryCount;

    public AverageCounter() {
        entryCount = 0;
    }

    public void update(long delta) {
        entryCount++;

        if (entryCount - 1 == 0) {
            averageValue = delta;
            return;
        }

        averageValue = (averageValue * (entryCount - 1) + delta) / entryCount;

    }

    public long getAverageValue() {
        return averageValue;
    }

    public void reset() {
        entryCount = 0;
        averageValue = 0;
    }

    public long getCounter() {
        return entryCount;
    }
}
