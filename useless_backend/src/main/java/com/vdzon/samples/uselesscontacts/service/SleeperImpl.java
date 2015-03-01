package com.vdzon.samples.uselesscontacts.service;

import javax.ejb.Stateless;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Stateless(name = "Sleeper")
public class SleeperImpl implements Sleeper {
    private final static Random randomGenerator = new Random();
    public static final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(16);
    private final int delayInMs;
    private final boolean random;

    private SleeperImpl() {
        this.delayInMs = 200;
        this.random = true;
    }


    public void sleep() {
        try {
            Thread.sleep(getDelayInMs());
        } catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }
    }

    public int getDelayInMs() {
        return (random ? randomGenerator.nextInt(10) : 1) * delayInMs;
    }

    public void schedule(Runnable r) {
        executor.schedule(r, getDelayInMs(), MILLISECONDS);
    }
}
