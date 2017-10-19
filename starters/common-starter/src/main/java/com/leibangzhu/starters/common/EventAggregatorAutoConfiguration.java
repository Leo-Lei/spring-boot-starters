package com.leibangzhu.starters.common;

import com.leibangzhu.starters.common.eventaggregator.IEventAggregator;
import com.leibangzhu.starters.common.eventaggregator.LocalEventAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventAggregatorAutoConfiguration {

    @Bean
    public IEventAggregator localEventAggregator(@Value("${event.aggregator.thread.pool.size:0}") int threadCount) {
        if ( 0 == threadCount){
            return new LocalEventAggregator();
        }else {
            return new LocalEventAggregator(threadCount);
        }
    }
}
