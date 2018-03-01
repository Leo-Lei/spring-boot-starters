package com.leibangzhu.starters.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.leibangzhu.starters.message.api.IMsgConsumer;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CacheBuilderTest {

    @Ignore
    @Test
    public void test() throws InterruptedException {
        ICache<String,String> cache = ICache
                .builder()   // key为String类型,value为String类型
                .maximumSize(3)         // 最多保存1000条数据
                .expireAfterWrite(5000, TimeUnit.SECONDS)
                .build();

        cache.put("hello","world");
        cache.put("A","100");
        cache.put("B","200");
        cache.put("C","300");
        cache.put("D","400");
        cache.put("E","500");
        cache.put("F","600");
        cache.put("G","700");
        cache.put("H","800");
        cache.put("I","900");

        System.out.println(cache.get("hello"));         // world
        System.out.println(cache.get("A"));             // 100
        cache.remove("hello");
        System.out.println(cache.get("hello"));         // null

        Thread.sleep(6 * 1000);

        System.out.println(cache.get("A"));             // null
    }

    @Ignore
    @Test
    public void test002(){

        ICache<String,String> cache = ICache
                .builder()
                .maximumSize(200)
                .consumer(createMsgConsumer())
                .build();
    }

    private IMsgConsumer createMsgConsumer(){
        return new MockMsgConsumer();
    }

    @Ignore
    @Test
    public void test003() throws InterruptedException {
        Cache<String,String> cache = Caffeine.newBuilder()
                .maximumSize(3)
                .build(k -> k);
        cache.put("A","100");
        cache.put("B","200");
        cache.put("C","300");
        cache.put("D","400");
        cache.put("E","500");
        cache.put("F","600");
        cache.put("G","700");
        cache.put("H","800");
        cache.put("I","900");

        Thread.sleep(3 * 1000);

        //cache.cleanUp();
        System.out.println(cache.estimatedSize());
    }



    static class DataObject {
        private String data;

        private static int objectCounter = 0;
        // standard constructors/getters

        public DataObject(String data){
            this.data = data;
        }

        public static DataObject get(String data) {
            objectCounter++;
            return new DataObject(data);
        }
    }
}
