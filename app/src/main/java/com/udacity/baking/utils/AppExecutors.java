package com.udacity.baking.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 *
 * @author Erick Prieto
 * @since 2018
 */
public class AppExecutors {

    private static final int NETWORK_THREAD_COUNT = 3;

    private static Executor diskIOExecutor;

    private static Executor networkIOExecutor;

    private static Executor mainThreadExecutor;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIOExecutor = diskIO;
        this.networkIOExecutor = networkIO;
        this.mainThreadExecutor = mainThread;
    }

    private AppExecutors() {
        this(Executors.newSingleThreadExecutor()
                , Executors.newFixedThreadPool(NETWORK_THREAD_COUNT)
                , new MainThreadExecutor());
    }

    public static Executor getDiskIO() {
        if (diskIOExecutor == null) { new AppExecutors(); }
        return diskIOExecutor;
    }

    public static Executor getNetworkIO() {
        if (networkIOExecutor == null) { new AppExecutors(); }
        return networkIOExecutor;
    }

    public static Executor getMainThread() {
        if(getMainThread() == null) { new AppExecutors(); }
        return mainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
