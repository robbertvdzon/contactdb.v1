package com.vdzon.samples.uselesscontacts.service;

import javax.ejb.Stateless;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public interface Sleeper {
    int getDelayInMs();

    void schedule(Runnable r);
}
