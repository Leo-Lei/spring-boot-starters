package com.leibangzhu.starters.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PrometheusHttpMetricFactory {

    private static Map<String,Counter> counterByName = new ConcurrentHashMap<>();
    private static Map<String,Gauge> gaugeByName = new ConcurrentHashMap<>();

    public static Counter getCounter(String name){
        if (!counterByName.containsKey(name)){
            counterByName.put(name,createCounter(name));
        }
        return counterByName.get(name);
    }

    public static Gauge getGauge(String name){
        if (!gaugeByName.containsKey(name)){
            gaugeByName.put(name,createGauge(name));
        }
        return gaugeByName.get(name);
    }

    private static Counter createCounter(String name){
        return Counter.build()
                .name("http_request_counter_" + name)
                .labelNames("appName","uri")
                .help("A counter to calculate http invoke amount for " + name)
                .register();
    }

    private static Gauge createGauge(String name){
        return Gauge.build()
                .name("http_request_elapsed_time_gauge_" + name)
                .labelNames("appName","uri")
                .help("A gauge to calculate http invoke elapsed time for " + name)
                .register();
    }
}
