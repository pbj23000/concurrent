package cj.mf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Created by cjm on 5/3/15.
 */
public class transformAndAct {
    public CompletableFuture<String> askString() {
        Object params = null;
        Executor executor = null;
        final CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor);

        return future;
    }

    private String longRunningTask(Object params) {
        return "42";
    }

    public CompletableFuture<Integer> askInt() {
        Object params = null;
        Executor executor = null;
        final CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor);
        final CompletableFuture<Integer> f2 = f1.thenApply(Integer::parseInt);
        return f2;
    }

    public CompletableFuture<Double> askDouble() {
        Object params = null;
        Executor executor = null;
        final CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor);
        final CompletableFuture<Integer> f2 = f1.thenApply(Integer::parseInt);
        final CompletableFuture<Double> f3 = f2.thenApply(r -> r * r * Math.PI);
        return f3;
    }

    public CompletableFuture<Double> askAllInOne() {
        Object params = null;
        Executor executor = null;
        CompletableFuture<Double> f3 =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor)
                .thenApply(Integer::parseInt)
                        .thenApply(r -> r * r * Math.PI);
        return f3;
    }
}
