package com.leibangzhu.starters.mq;

public interface IMsgHandler {
    MsgHandleStatus process(Message msg);

    enum MsgHandleStatus {
        SUCCESS,FAIL
    }
}
