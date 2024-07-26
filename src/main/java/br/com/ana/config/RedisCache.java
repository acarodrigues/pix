package br.com.ana.config;

import br.com.ana.model.ChavePix;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.SetArgs;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

@ApplicationScoped
public class RedisCache {
    private ValueCommands<String, ChavePix> commands;

    public RedisCache(RedisDataSource redisDataSource) {
        this.commands = redisDataSource.value(ChavePix.class);
    }

    public ChavePix get(String key){
        return commands.get(key);
    }

    public ChavePix getOrSetIfAbsent(String key, Supplier<ChavePix> cachedCh){

        var cached = get(key);
        if (Objects.nonNull(cached)){
            return cached;
        } else{
            var result = cachedCh.get();
            set(key,result);
            return result;
        }
    }

    public void set(String key, ChavePix cached){
        commands.set(key,cached, new SetArgs().ex(Duration.ofMinutes(30)));
    }

    public void evict(String key){
        commands.getdel(key);
    }





}
