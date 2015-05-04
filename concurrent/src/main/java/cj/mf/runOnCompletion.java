package cj.mf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import cj.mf.domain.Document;
import org.slf4j.Logger;

/**
 * Created by cjm on 5/3/15.
 */
public class runOnCompletion {
    public CompletableFuture<Double> ask() {
        Object params = null;
        Executor executor = null;
        Logger log = null;
        final CompletableFuture<Double> future =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor)
                        .thenApply(Integer::parseInt)
                        .thenApply(r -> r * r * Math.PI);
        future.thenAcceptAsync(dbl -> log.debug("Result: {}", dbl), executor);
        log.debug("Continuing");
        return future;
    }

    public CompletableFuture<String> ask2() {
        Object params = null;
        Executor executor = null;
        final CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor);
        final CompletableFuture<String> safe =
                future.exceptionally(ex -> "We have a problem: " + ex.getMessage());
        return safe;
    }

    public CompletableFuture<Integer> ask3() {
        Object params = null;
        Executor executor = null;
        Logger log = null;
        final CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> longRunningTask(params), executor);
        final CompletableFuture<Integer> safe =
                future.handle((ok, ex) -> {
                    if (ok != null) {
                        return Integer.parseInt(ok);
                    } else {
                        log.warn("Problem", ex);
                        return -1;
                    }
                });
        return safe;
    }

    public CompletableFuture<Double> ask4() {
        Object params = null;
        Executor executor = null;
        final CompletableFuture<Document> docFuture =
                CompletableFuture.supplyAsync(() -> longRunningDocTask(params), executor);
        final CompletableFuture<CompletableFuture<Double>> f =
                docFuture.thenApply(this::calculateRelevance);
        final CompletableFuture<Double> relevanceFuture =
                docFuture.thenCompose(this::calculateRelevance);
        return relevanceFuture;
    }

    /*
    public CompletableFuture<Double> ask5() {

    }
    */

    private String longRunningTask(Object params) {
        // ... long running task ...
        return "42";
    }

    private Document longRunningDocTask(Object params) {
        // ... long running task ...
        return new Document();
    }

    private CompletableFuture<Double> calculateRelevance(Document document) {
        Executor executor = null;
        final CompletableFuture<Double> relevanceFuture =
                CompletableFuture.supplyAsync(() -> returnRelevance(document), executor);
        return relevanceFuture;
    }

    private Double returnRelevance(Document d) {
        // ... long running process ...
        return .42;
    }
}
