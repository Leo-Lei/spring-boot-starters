package com.leibangzhu.starters.common;

import com.leibangzhu.starters.common.eventaggregator.IEventAggregator;
import com.leibangzhu.starters.common.eventaggregator.LocalEventAggregator;
import com.leibangzhu.starters.common.http.properties.HttpClientProperties;
import org.junit.Ignore;
import org.junit.Test;


public class EventAggregatorTest {

    @Ignore
    @Test
    public void test() throws Exception {
        HttpClientProperties httpClientProperties = new HttpClientProperties();



        IEventAggregator eventAggregator = new LocalEventAggregator();

        FooEvent event = new FooEvent();

        eventAggregator.subscribe(event,new HelloHandler());
        eventAggregator.subscribe(event,new WorldHandler());
        eventAggregator.publish(event);
        Thread.sleep(10 * 1000);
    }
}
