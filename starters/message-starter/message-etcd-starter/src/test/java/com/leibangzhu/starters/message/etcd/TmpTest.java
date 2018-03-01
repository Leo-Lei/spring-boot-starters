package com.leibangzhu.starters.message.etcd;

import com.leibangzhu.starters.common.util.JsonUtil;
import com.leibangzhu.starters.message.api.IMsgConsumer;
import com.leibangzhu.starters.message.api.IMsgHandler;
import com.leibangzhu.starters.message.api.IMsgProducer;
import com.leibangzhu.starters.message.api.Message;
import com.leibangzhu.starters.message.etcd.EtcdMsgConsumer;
import com.leibangzhu.starters.message.etcd.EtcdMsgProducer;
import org.junit.Ignore;
import org.junit.Test;

public class TmpTest {

    @Ignore
    @Test
    public void testProducer(){

        IMsgProducer producer = EtcdMsgProducer.newBuilder()
                .endpoints("http://127.0.0.1:2379")
                .build();

        Message message = Message.builder()
                .topic("test123")
                .tag("hello")
                .body(JsonUtil.serialize(new POJO("leo",20)))
                .build();

        producer.send(message);
    }

    @Ignore
    @Test
    public void testConsumer() throws InterruptedException {
        IMsgConsumer consumer = EtcdMsgConsumer.builder()
                .endpoints("http://127.0.0.1:2379")
                .topic("test123")
                .tags("*")
                .registerHandler(new MockMsgHandler())
                .build();
        consumer.start();
        Thread.sleep(1000 * 1000);
    }

    public static class MockMsgHandler implements IMsgHandler {

        @Override
        public void handle(Message message) {
            System.out.println(message.getBody());
            System.out.println("topic:" + message.getHeader("topic"));
            System.out.println("tag:" + message.getHeader("tag"));
        }
    }

    public static class POJO{
        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        private String name;
        private int age;

        public POJO(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
