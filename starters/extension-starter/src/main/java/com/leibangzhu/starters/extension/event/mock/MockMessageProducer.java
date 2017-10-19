package com.leibangzhu.starters.extension.event.mock;

import com.leibangzhu.starters.common.util.QibeiLogger;
import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MsgSendResult;
import com.leibangzhu.starters.mq.exception.MQException;
import com.leibangzhu.starters.mq.ons.IOnsProducer;
import org.slf4j.Logger;

/**
 * Created by reinhard on 06/06/2017.
 */
public class MockMessageProducer implements IOnsProducer {

    private static final Logger logger = QibeiLogger.create(MockMessageProducer.class);

    public void start() throws MQException {

        logger.info("Mocked message producer is started.");
    }

    public void shutdown() {

        logger.info("Mocked message producer is shutdown.");
    }

    public MsgSendResult send(Message message) throws MQException {

        logger.info(String.format("Message [%s] is sent.", message.toString()));
        return new MsgSendResult("mock_id", "success");
    }

    public void sendOneway(Message message) throws MQException {

        logger.info(String.format("Message [%s] is sent by one way.", message.toString()));
    }
}
