package com.leibangzhu.starters.extension.event.mock;

import com.leibangzhu.starters.common.util.QibeiLogger;
import com.leibangzhu.starters.mq.IMsgHandler;
import com.leibangzhu.starters.mq.exception.MQException;
import com.leibangzhu.starters.mq.ons.IOnsConsumer;
import org.slf4j.Logger;

/**
 * Created by reinhard on 06/06/2017.
 */
public class MockMessageConsumer implements IOnsConsumer {

    private static final Logger logger = QibeiLogger.create(MockMessageConsumer.class);

    public void start() throws MQException {

        logger.info("Mocked message consumer is started.");
    }

    public void shutdown() {

        logger.info("Mocked message consumer is shutdown.");
    }

    public void subscribe(String topic, String subExpression, IMsgHandler handler) throws MQException {

        logger.info("Subscription is success, but since it is in mock mode, it would not be effected actually.");
    }

    public void subscribe(String topic, IMsgHandler handler) throws MQException {

        logger.info("Subscription is success, but since it is in mock mode, it would not be effected actually.");
    }

    @Override
    public void unsubscribe(String topic) {

        logger.info("Unsubscription is success.");
    }
}
