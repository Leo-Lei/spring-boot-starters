package com.leibangzhu.starters.mq;

import com.leibangzhu.starters.mq.rocketmq.RocketMqConsumerBuilder;
import com.leibangzhu.starters.mq.rocketmq.RocketMqProducerBuilder;

public class MQ {
    public static class RocketMQ {

        public static RocketMqProducerBuilder producer(){
            return RocketMqProducerBuilder.builder();
        }

        public static RocketMqConsumerBuilder consumer(){
            return RocketMqConsumerBuilder.builder();
        }
    }

    public static class ONS {
        // to be added...
    }
}
