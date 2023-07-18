package com.example.moviesapp.utilities;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BackgroundExecutors {

    private static BackgroundExecutors backgroundExecutorsInstance;

    public static BackgroundExecutors getInstance() {
        if (backgroundExecutorsInstance == null) {
            backgroundExecutorsInstance = new BackgroundExecutors();
        }
        return backgroundExecutorsInstance;
    }

    private final ScheduledExecutorService networkIOService = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO() {
        return networkIOService;
    }
}
