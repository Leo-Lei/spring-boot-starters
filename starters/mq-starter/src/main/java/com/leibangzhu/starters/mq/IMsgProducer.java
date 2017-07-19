package com.leibangzhu.starters.mq;

public interface IMsgProducer {
    MsgSendResult send(Message msg);
}
