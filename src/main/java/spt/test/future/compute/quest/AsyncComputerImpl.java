package spt.test.future.compute.quest;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.*;
import java.util.function.Function;

public class AsyncComputerImpl<K, V> {

    private final AsyncCache<K, V> cache;

    /*
     Статическое поле не может принимать "нормальные" параметры типо K и V.
     Поэтому ставим ?, как бы намекая разработчикам на то, что они тут есть.
     */
    private static volatile AsyncComputerImpl<?, ?> computer;

    public Future<V> compute(K k, Function<K, V> f) {
        return cache.get(k, f);
    }

    private AsyncComputerImpl(){
        cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .recordStats()
                .buildAsync();
    }

    public static <K, V> AsyncComputerImpl<K, V> getInstance(){
        // Преобразование типа делаем тут. чтобы не делать это каждый раз, вызывая getInstance снаружи.
        AsyncComputerImpl<K, V> local = (AsyncComputerImpl<K, V>) computer;
        if (local == null){
            synchronized (AsyncComputerImpl.class){
                local = (AsyncComputerImpl<K, V>) computer;
                if (local == null){
                    computer = local = new AsyncComputerImpl<>();
                }
            }
        }
        return local;
    }

    public long getHitCount(){
        return cache.synchronous().stats().hitCount();
    }

    public long getMissCount(){
        return cache.synchronous().stats().missCount();
    }
}
