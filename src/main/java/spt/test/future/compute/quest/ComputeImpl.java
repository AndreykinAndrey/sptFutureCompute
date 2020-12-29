package spt.test.future.compute.quest;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.function.Function;

public class ComputeImpl<K, V> {
    private final Cache<K, V> cache;

    private static volatile ComputeImpl<?, ?> computer;


    public Future<V> compute(K k, Function<K, V> f) {
        Callable<V> task = () -> cache.get(k, f);

        RunnableFuture<V> ff = new FutureTask<>(task);

        ff.run();
        return ff;

    }

    private ComputeImpl(){
        cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .recordStats()
                .build();
    }

    public static <K, V> ComputeImpl<K, V> getInstance(){
        ComputeImpl<K, V> local = (ComputeImpl<K, V>) computer;
        if (local == null){
            synchronized (AsyncComputerImpl.class){
                local = (ComputeImpl<K, V>) computer;
                if (local == null){
                    computer = local = new ComputeImpl<>();
                }
            }
        }
        return local;
    }

    public long getHitCount(){
        return cache.stats().hitCount();
    }

    public long getMissCount(){
        return cache.stats().missCount();
    }
}
