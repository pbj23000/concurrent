package cj.mf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * Created by cjm on 5/3/15.
 */
public class tut {
    public CompletableFuture<String> ask() {
        final CompletableFuture<String> future = new CompletableFuture<String>();
        // ...
        return future;
    }

    public CompletableFuture<String> ask1() {
        Executor executor = null;
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            public String get() {
                // ...long running....
                return "42";
            }
        }, executor);
        return future;
    }

    public CompletableFuture<String> ask2() {
        Executor executor = null;
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // ... long running ...
            return "42";
        }, executor);
        return future;
    }

    public CompletableFuture<String> ask3() {
        Object params = null;
        Executor executor = null;
        final CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor);
        return future;
    }

    private String longRunningTask(Object o) { return "42"; }
}
