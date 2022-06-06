package com.lon.game.utils;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class AverageCounterMap extends HashMap<String, AverageCounter> {

    public void update(long value) {
        for (AverageCounter counter: values()) {
            counter.update(value);
        }
    }

    public void reset(String id) {
        AverageCounter counter = get(id);

        if (counter != null) {
            counter.reset();
            return;
        }

        throw new NoSuchElementException();
    }

    public float getAverage(String id) {
        AverageCounter counter = get(id);

        if (counter != null) {
            return counter.getAverageValue();
        }

        throw new NoSuchElementException();
    }

    public long getCounter(String id) {
        AverageCounter counter = get(id);

        if (counter != null) {
            return counter.getCounter();
        }

        throw new NoSuchElementException();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String id: keySet()) {
            AverageCounter counter = get(id);
            stringBuilder.append(String.format("id: %s\tcounter: %d\tavg: %d\n", id, counter.getCounter(), counter.getAverageValue()));
        }

        return stringBuilder.toString();
    }
}
