package com.github.v1ctu.util;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Task {

    private static final Executor executor = new ThreadPoolExecutor(
        1, 3, 20, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>()
    );

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor).exceptionally(exception -> {
            System.err.println("exception: " + exception);
            return null;
        });
    }

    public static <U> CompletableFuture<U> whenCompleteAsync(CompletableFuture<U> future, BiConsumer<U, Throwable> consumer) {
        return future.whenCompleteAsync(consumer);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

}

